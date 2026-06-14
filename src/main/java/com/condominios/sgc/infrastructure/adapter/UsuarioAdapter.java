package com.condominios.sgc.infrastructure.adapter;

import com.condominios.sgc.domain.auxiliar.Rol;
import com.condominios.sgc.domain.dto.request.PaginacionRequest;
import com.condominios.sgc.domain.dto.response.PaginacionResponse;
import com.condominios.sgc.domain.filter.UsuarioFilter;
import com.condominios.sgc.domain.model.UsuarioModel;
import com.condominios.sgc.domain.port.UsuarioPort;
import com.condominios.sgc.infrastructure.persistence.entity.UsuarioEntity;
import com.condominios.sgc.infrastructure.persistence.mapper.UsuarioMapper;
import com.condominios.sgc.infrastructure.persistence.repository.UsuarioRepository;
import com.condominios.sgc.infrastructure.persistence.specification.UsuarioSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class UsuarioAdapter implements UsuarioPort {

    private final UsuarioRepository repository;
    private final UsuarioMapper mapper;

    public UsuarioAdapter(UsuarioRepository repository, UsuarioMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public UsuarioModel guardar(UsuarioModel modelo) {
        var entidad = mapper.aEntidad(modelo);
        return mapper.aModelo(repository.save(entidad));
    }

    @Override
    public Optional<UsuarioModel> obtenerPorId(Long id) {
        return repository.findById(id).map(mapper::aModelo);
    }

    @Override
    public Optional<UsuarioModel> obtenerPorCorreo(String correo) {
        return repository.findByCorreo(correo).map(mapper::aModelo);
    }

    @Override
    public List<UsuarioModel> obtenerTodos() {
        return repository.findAll().stream().map(mapper::aModelo).toList();
    }

    @Override
    public PaginacionResponse<UsuarioModel> obtenerTodos(PaginacionRequest request, UsuarioFilter filtro) {
        Pageable pageable = PageRequest.of(request.pagina(), request.tamano());
        Page<UsuarioEntity> pagina = repository.findAll(UsuarioSpecification.conFiltro(filtro), pageable);
        List<UsuarioModel> contenido = pagina.getContent().stream().map(mapper::aModelo).toList();
        return new PaginacionResponse<>(contenido, pagina.getNumber(), pagina.getSize(),
            pagina.getTotalElements(), pagina.getTotalPages());
    }

    @Override
    public List<UsuarioModel> obtenerPorRol(Rol rol) {
        return repository.findByRol(rol).stream().map(mapper::aModelo).toList();
    }

    @Override
    public List<UsuarioModel> obtenerPorCondominio(Long idCondominio) {
        return repository.findByIdCondominio(idCondominio).stream().map(mapper::aModelo).toList();
    }

    @Override
    public void eliminarPorId(Long id) {
        repository.deleteById(id);
    }
}

package com.condominios.sgc.infrastructure.adapter;

import com.condominios.sgc.domain.filter.UsuarioFilter;
import com.condominios.sgc.domain.model.UsuarioModel;
import com.condominios.sgc.domain.pagination.Pagina;
import com.condominios.sgc.domain.pagination.Paginable;
import com.condominios.sgc.domain.port.UsuarioPort;
import com.condominios.sgc.domain.auxiliar.Rol;
import com.condominios.sgc.infrastructure.persistence.entity.UsuarioEntity;
import com.condominios.sgc.infrastructure.persistence.mapper.UsuarioMapper;
import com.condominios.sgc.infrastructure.persistence.repository.UsuarioRepository;
import com.condominios.sgc.infrastructure.persistence.specification.UsuarioSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public UsuarioModel guardar(UsuarioModel usuario) {
        UsuarioEntity entidad = mapper.aEntidad(usuario);
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
    public Pagina<UsuarioModel> obtenerTodos(Paginable paginable, UsuarioFilter filtro) {
        PageRequest pageRequest = PageRequest.of(paginable.pagina(), paginable.tamano());
        Page<UsuarioEntity> page = repository.findAll(UsuarioSpecification.conFiltro(filtro), pageRequest);
        List<UsuarioModel> contenido = page.getContent().stream().map(mapper::aModelo).toList();
        return new Pagina<>(contenido, page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages());
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
    public int contarPorCondominio(Long idCondominio) {
        return repository.findByIdCondominio(idCondominio).size();
    }

    @Override
    public void eliminarPorId(Long id) {
        repository.deleteById(id);
    }
}

package com.condominios.sgc.infrastructure.adapter;

import com.condominios.sgc.domain.dto.request.PaginacionRequest;
import com.condominios.sgc.domain.dto.response.PaginacionResponse;
import com.condominios.sgc.domain.filter.VehiculoFilter;
import com.condominios.sgc.domain.model.VehiculoModel;
import com.condominios.sgc.domain.port.VehiculoPort;
import com.condominios.sgc.infrastructure.persistence.entity.VehiculoEntity;
import com.condominios.sgc.infrastructure.persistence.mapper.VehiculoMapper;
import com.condominios.sgc.infrastructure.persistence.repository.VehiculoRepository;
import com.condominios.sgc.infrastructure.persistence.specification.VehiculoSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class VehiculoAdapter implements VehiculoPort {

    private final VehiculoRepository repository;
    private final VehiculoMapper mapper;

    public VehiculoAdapter(VehiculoRepository repository, VehiculoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public VehiculoModel guardar(VehiculoModel modelo) {
        var entidad = mapper.aEntidad(modelo);
        return mapper.aModelo(repository.save(entidad));
    }

    @Override
    public Optional<VehiculoModel> obtenerPorId(Long id) {
        return repository.findById(id).map(mapper::aModelo);
    }

    @Override
    public Optional<VehiculoModel> obtenerPorPlaca(String placa) {
        return repository.findByPlaca(placa).map(mapper::aModelo);
    }

    @Override
    public List<VehiculoModel> obtenerPorPropietario(Long idPropietario) {
        return repository.findByIdPropietario(idPropietario).stream().map(mapper::aModelo).toList();
    }

    @Override
    public List<VehiculoModel> obtenerPorInquilino(Long idInquilino) {
        return repository.findByIdInquilino(idInquilino).stream().map(mapper::aModelo).toList();
    }

    @Override
    public List<VehiculoModel> obtenerPorCondominio(Long idCondominio) {
        return repository.findByIdCondominio(idCondominio).stream().map(mapper::aModelo).toList();
    }

    @Override
    public List<VehiculoModel> obtenerTodos() {
        return repository.findAll().stream().map(mapper::aModelo).toList();
    }

    @Override
    public PaginacionResponse<VehiculoModel> obtenerTodos(PaginacionRequest request, VehiculoFilter filtro) {
        Pageable pageable = PageRequest.of(request.pagina(), request.tamano());
        Page<VehiculoEntity> pagina = repository.findAll(VehiculoSpecification.conFiltro(filtro), pageable);
        List<VehiculoModel> contenido = pagina.getContent().stream().map(mapper::aModelo).toList();
        return new PaginacionResponse<>(contenido, pagina.getNumber(), pagina.getSize(),
            pagina.getTotalElements(), pagina.getTotalPages());
    }

    @Override
    public void eliminarPorId(Long id) {
        repository.deleteById(id);
    }
}

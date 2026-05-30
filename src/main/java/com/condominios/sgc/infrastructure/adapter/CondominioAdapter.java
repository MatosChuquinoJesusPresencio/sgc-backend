package com.condominios.sgc.infrastructure.adapter;

import java.util.Optional;

import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.CondominioModel;
import com.condominios.sgc.domain.port.CondominioPort;
import com.condominios.sgc.infrastructure.persistence.entity.CondominioEntity;
import com.condominios.sgc.infrastructure.persistence.mapper.CondominioMapper;
import com.condominios.sgc.infrastructure.persistence.repository.CondominioRepository;
import com.condominios.sgc.infrastructure.persistence.specification.CondominioSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class CondominioAdapter implements CondominioPort {

    private final CondominioRepository condominioRepository;

    public CondominioAdapter(CondominioRepository condominioRepository) {
        this.condominioRepository = condominioRepository;
    }

    @Override
    public Optional<CondominioModel> findById(Long id) {
        return condominioRepository.findById(id).map(CondominioMapper::toModel);
    }

    @Override
    public PaginacionResponse<CondominioModel> buscarPaginado(PaginacionRequest request) {
        var orden = request.direccion() != null && request.direccion().equalsIgnoreCase("DESC")
                ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = request.ordenarPor() != null
                ? PageRequest.of(request.pagina(), request.tamanio(), orden, request.ordenarPor())
                : PageRequest.of(request.pagina(), request.tamanio());
        var spec = CondominioSpecifications.fromFiltros(request.filtros());
        Page<CondominioEntity> page = condominioRepository.findAll(spec, pageable);
        return PaginacionResponse.de(
                page.getContent().stream().map(CondominioMapper::toModel).toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    @Override
    public CondominioModel save(CondominioModel model) {
        CondominioEntity entity = CondominioMapper.toEntity(model);
        CondominioEntity savedEntity = condominioRepository.save(entity);
        return CondominioMapper.toModel(savedEntity);
    }

    @Override
    public void deleteById(Long id) {
        condominioRepository.deleteById(id);
    }

    @Override
    public boolean existsByNombre(String nombre) {
        return condominioRepository.existsByNombre(nombre);
    }
}

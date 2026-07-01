package com.condominios.sgc.infrastructure.adapter.out.persistence;

import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import com.condominios.sgc.application.dto.query.PaginaQuery;
import com.condominios.sgc.application.dto.result.PaginaResult;
import com.condominios.sgc.application.port.out.EstacionamientoRepositoryPort;
import com.condominios.sgc.domain.model.EstacionamientoModel;
import com.condominios.sgc.infrastructure.adapter.out.persistence.mapper.EstacionamientoMapper;
import com.condominios.sgc.infrastructure.adapter.out.persistence.repository.EstacionamientoJpaRepository;

@Component
public class EstacionamientoRepositoryAdapter implements EstacionamientoRepositoryPort {

    private final EstacionamientoJpaRepository repository;

    public EstacionamientoRepositoryAdapter(EstacionamientoJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<EstacionamientoModel> buscarPorId(Long id) {
        return repository.findById(id).map(EstacionamientoMapper::toModel);
    }

    @Override
    public EstacionamientoModel guardar(EstacionamientoModel modelo) {
        return EstacionamientoMapper.toModel(repository.save(EstacionamientoMapper.toEntity(modelo)));
    }

    @Override
    public long contarPorApartamento(Long idApartamento) {
        return repository.countByIdApartamento(idApartamento);
    }

    @Override
    public void eliminarPorId(Long id) {
        repository.deleteById(id);
    }

    @Override
    public PaginaResult<EstacionamientoModel> buscarPorCondominio(Long idCondominio, PaginaQuery paginacion) {
        var pageable = PageRequest.of(paginacion.pagina(), paginacion.tamano());
        var page = repository.findByIdCondominio(idCondominio, pageable);
        var items = page.getContent().stream().map(EstacionamientoMapper::toModel).toList();
        return new PaginaResult<>(items, page.getTotalElements(), page.getNumber(), page.getSize());
    }
}

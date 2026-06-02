package com.condominios.sgc.infrastructure.adapter;

import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.EstacionamientoModel;
import com.condominios.sgc.domain.exception.ApartamentoException;
import com.condominios.sgc.domain.exception.CondominioException;
import com.condominios.sgc.domain.port.EstacionamientoPort;
import com.condominios.sgc.infrastructure.persistence.entity.EstacionamientoEntity;
import com.condominios.sgc.infrastructure.persistence.mapper.EstacionamientoMapper;
import com.condominios.sgc.infrastructure.persistence.repository.ApartamentoRepository;
import com.condominios.sgc.infrastructure.persistence.repository.CondominioRepository;
import com.condominios.sgc.infrastructure.persistence.repository.EstacionamientoRepository;
import com.condominios.sgc.infrastructure.persistence.specification.EstacionamientoSpecifications;
import com.condominios.sgc.infrastructure.util.PaginacionUtil;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EstacionamientoAdapter implements EstacionamientoPort {

    private final EstacionamientoRepository estacionamientoRepository;
    private final CondominioRepository condominioRepository;
    private final ApartamentoRepository apartamentoRepository;

    public EstacionamientoAdapter(EstacionamientoRepository estacionamientoRepository,
                                  CondominioRepository condominioRepository,
                                  ApartamentoRepository apartamentoRepository) {
        this.estacionamientoRepository = estacionamientoRepository;
        this.condominioRepository = condominioRepository;
        this.apartamentoRepository = apartamentoRepository;
    }

    @Override
    public Optional<EstacionamientoModel> findById(Long id) {
        return estacionamientoRepository.findById(id).map(EstacionamientoMapper::toModel);
    }

    @Override
    public PaginacionResponse<EstacionamientoModel> findByApartamentoId(Long apartamentoId, PaginacionRequest request) {
        var filtros = request.filtros();
        Specification<EstacionamientoEntity> spec;
        if (filtros != null && !filtros.isEmpty()) {
            spec = EstacionamientoSpecifications.fromFiltros(filtros);
        } else {
            spec = EstacionamientoSpecifications.porApartamentoId(apartamentoId);
        }
        var page = estacionamientoRepository.findAll(spec, PaginacionUtil.toPageable(request));
        return PaginacionUtil.toPaginacionResponse(page, page.map(EstacionamientoMapper::toModel).toList());
    }

    @Override
    public PaginacionResponse<EstacionamientoModel> findByCondominioId(Long condominioId, PaginacionRequest request) {
        var filtros = request.filtros();
        Specification<EstacionamientoEntity> spec;
        if (filtros != null && !filtros.isEmpty()) {
            spec = EstacionamientoSpecifications.fromFiltros(filtros);
        } else {
            spec = EstacionamientoSpecifications.porCondominioId(condominioId);
        }
        var page = estacionamientoRepository.findAll(spec, PaginacionUtil.toPageable(request));
        return PaginacionUtil.toPaginacionResponse(page, page.map(EstacionamientoMapper::toModel).toList());
    }

    @Override
    public EstacionamientoModel save(EstacionamientoModel model) {
        var entity = EstacionamientoMapper.toEntity(model);
        entity.setCondominio(condominioRepository.findById(model.getCondominioId())
            .orElseThrow(() -> CondominioException.noEncontrado(model.getCondominioId())));
        if (model.getApartamentoId() != null) {
            entity.setApartamento(apartamentoRepository.findById(model.getApartamentoId())
                .orElseThrow(() -> ApartamentoException.noEncontrado(model.getApartamentoId())));
        }
        var saved = estacionamientoRepository.save(entity);
        return EstacionamientoMapper.toModel(saved);
    }

    @Override
    public void deleteById(Long id) {
        estacionamientoRepository.deleteById(id);
    }
}

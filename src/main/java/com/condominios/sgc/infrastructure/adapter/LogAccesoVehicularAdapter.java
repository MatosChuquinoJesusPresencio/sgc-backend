package com.condominios.sgc.infrastructure.adapter;

import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.LogAccesoVehicularModel;
import com.condominios.sgc.domain.exception.EstacionamientoException;
import com.condominios.sgc.domain.exception.VehiculoException;
import com.condominios.sgc.domain.port.LogAccesoVehicularPort;
import com.condominios.sgc.infrastructure.persistence.entity.LogAccesoVehicularEntity;
import com.condominios.sgc.infrastructure.persistence.mapper.LogAccesoVehicularMapper;
import com.condominios.sgc.infrastructure.persistence.repository.EstacionamientoRepository;
import com.condominios.sgc.infrastructure.persistence.repository.LogAccesoVehicularRepository;
import com.condominios.sgc.infrastructure.persistence.repository.VehiculoRepository;
import com.condominios.sgc.infrastructure.persistence.specification.LogAccesoVehicularSpecifications;
import com.condominios.sgc.infrastructure.util.PaginacionUtil;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LogAccesoVehicularAdapter implements LogAccesoVehicularPort {

    private final LogAccesoVehicularRepository logAccesoVehicularRepository;
    private final VehiculoRepository vehiculoRepository;
    private final EstacionamientoRepository estacionamientoRepository;

    public LogAccesoVehicularAdapter(LogAccesoVehicularRepository logAccesoVehicularRepository,
                                     VehiculoRepository vehiculoRepository,
                                     EstacionamientoRepository estacionamientoRepository) {
        this.logAccesoVehicularRepository = logAccesoVehicularRepository;
        this.vehiculoRepository = vehiculoRepository;
        this.estacionamientoRepository = estacionamientoRepository;
    }

    @Override
    public Optional<LogAccesoVehicularModel> findById(Long id) {
        return logAccesoVehicularRepository.findById(id).map(LogAccesoVehicularMapper::toModel);
    }

    @Override
    public PaginacionResponse<LogAccesoVehicularModel> findByCondominioId(Long condominioId, PaginacionRequest request) {
        var filtros = request.filtros();
        Specification<LogAccesoVehicularEntity> spec;
        if (filtros != null && !filtros.isEmpty()) {
            spec = LogAccesoVehicularSpecifications.fromFiltros(filtros);
        } else {
            spec = LogAccesoVehicularSpecifications.porCondominioId(condominioId);
        }
        var page = logAccesoVehicularRepository.findAll(spec, PaginacionUtil.toPageable(request));
        return PaginacionUtil.toPaginacionResponse(page, page.map(LogAccesoVehicularMapper::toModel).toList());
    }

    @Override
    public PaginacionResponse<LogAccesoVehicularModel> findByApartamentoId(Long apartamentoId, PaginacionRequest request) {
        var filtros = request.filtros();
        Specification<LogAccesoVehicularEntity> spec;
        if (filtros != null && !filtros.isEmpty()) {
            spec = LogAccesoVehicularSpecifications.fromFiltros(filtros);
        } else {
            spec = LogAccesoVehicularSpecifications.porApartamentoId(apartamentoId);
        }
        var page = logAccesoVehicularRepository.findAll(spec, PaginacionUtil.toPageable(request));
        return PaginacionUtil.toPaginacionResponse(page, page.map(LogAccesoVehicularMapper::toModel).toList());
    }

    @Override
    public LogAccesoVehicularModel save(LogAccesoVehicularModel model) {
        var entity = LogAccesoVehicularMapper.toEntity(model);
        if (model.getVehiculoId() != null) {
            entity.setVehiculo(vehiculoRepository.findById(model.getVehiculoId())
                .orElseThrow(() -> VehiculoException.noEncontrado(model.getVehiculoId())));
        }
        if (model.getEstacionamientoId() != null) {
            entity.setEstacionamiento(estacionamientoRepository.findById(model.getEstacionamientoId())
                .orElseThrow(() -> EstacionamientoException.noEncontrado(model.getEstacionamientoId())));
        }
        var saved = logAccesoVehicularRepository.save(entity);
        return LogAccesoVehicularMapper.toModel(saved);
    }
}

package com.condominios.sgc.infrastructure.adapter;

import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.LogAccesoVehicularModel;
import com.condominios.sgc.domain.port.LogAccesoVehicularPort;
import com.condominios.sgc.infrastructure.persistence.mapper.LogAccesoVehicularMapper;
import com.condominios.sgc.infrastructure.persistence.repository.EstacionamientoRepository;
import com.condominios.sgc.infrastructure.persistence.repository.LogAccesoVehicularRepository;
import com.condominios.sgc.infrastructure.persistence.repository.VehiculoRepository;
import com.condominios.sgc.infrastructure.util.PaginacionUtil;

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
        var page = logAccesoVehicularRepository.findByCondominioId(condominioId, PaginacionUtil.toPageable(request));
        return PaginacionUtil.toPaginacionResponse(page, page.map(LogAccesoVehicularMapper::toModel).toList());
    }

    @Override
    public PaginacionResponse<LogAccesoVehicularModel> findByApartamentoId(Long apartamentoId, PaginacionRequest request) {
        var page = logAccesoVehicularRepository.findByApartamentoId(apartamentoId, PaginacionUtil.toPageable(request));
        return PaginacionUtil.toPaginacionResponse(page, page.map(LogAccesoVehicularMapper::toModel).toList());
    }

    @Override
    public LogAccesoVehicularModel save(LogAccesoVehicularModel model) {
        var entity = LogAccesoVehicularMapper.toEntity(model);
        if (model.getVehiculoId() != null) {
            entity.setVehiculo(vehiculoRepository.findById(model.getVehiculoId())
                .orElseThrow(() -> new RuntimeException("Vehiculo no encontrado: " + model.getVehiculoId())));
        }
        if (model.getEstacionamientoId() != null) {
            entity.setEstacionamiento(estacionamientoRepository.findById(model.getEstacionamientoId())
                .orElseThrow(() -> new RuntimeException("Estacionamiento no encontrado: " + model.getEstacionamientoId())));
        }
        var saved = logAccesoVehicularRepository.save(entity);
        return LogAccesoVehicularMapper.toModel(saved);
    }
}

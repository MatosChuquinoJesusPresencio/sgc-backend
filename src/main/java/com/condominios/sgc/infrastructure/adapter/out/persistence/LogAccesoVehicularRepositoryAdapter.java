package com.condominios.sgc.infrastructure.adapter.out.persistence;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.condominios.sgc.application.port.out.LogAccesoVehicularRepositoryPort;
import com.condominios.sgc.domain.model.LogAccesoVehicularModel;
import com.condominios.sgc.infrastructure.adapter.out.persistence.mapper.LogAccesoVehicularMapper;
import com.condominios.sgc.infrastructure.adapter.out.persistence.repository.LogAccesoVehicularJpaRepository;

@Component
public class LogAccesoVehicularRepositoryAdapter implements LogAccesoVehicularRepositoryPort {

    private final LogAccesoVehicularJpaRepository repository;

    public LogAccesoVehicularRepositoryAdapter(LogAccesoVehicularJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<LogAccesoVehicularModel> buscarPorId(Long id) {
        return repository.findById(id).map(LogAccesoVehicularMapper::toModel);
    }

    @Override
    public LogAccesoVehicularModel guardar(LogAccesoVehicularModel modelo) {
        return LogAccesoVehicularMapper.toModel(repository.save(LogAccesoVehicularMapper.toEntity(modelo)));
    }

    @Override
    public void eliminarPorId(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Page<LogAccesoVehicularModel> buscarPorCondominio(
            Long idCondominio, Long userId, Instant fechaInicio, Instant fechaFin, Pageable pageable) {
        var inicio = fechaInicio != null ? LocalDateTime.ofInstant(fechaInicio, ZoneOffset.UTC) : null;
        var fin = fechaFin != null ? LocalDateTime.ofInstant(fechaFin, ZoneOffset.UTC) : null;
        return repository.findByFilters(idCondominio, userId, inicio, fin, pageable)
                .map(LogAccesoVehicularMapper::toModel);
    }
}

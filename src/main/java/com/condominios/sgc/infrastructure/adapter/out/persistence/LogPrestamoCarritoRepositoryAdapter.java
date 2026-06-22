package com.condominios.sgc.infrastructure.adapter.out.persistence;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.condominios.sgc.application.port.out.LogPrestamoCarritoRepositoryPort;
import com.condominios.sgc.domain.model.LogPrestamoCarritoModel;
import com.condominios.sgc.infrastructure.adapter.out.persistence.mapper.LogPrestamoCarritoMapper;
import com.condominios.sgc.infrastructure.adapter.out.persistence.repository.LogPrestamoCarritoJpaRepository;

@Component
public class LogPrestamoCarritoRepositoryAdapter implements LogPrestamoCarritoRepositoryPort {

    private final LogPrestamoCarritoJpaRepository repository;

    public LogPrestamoCarritoRepositoryAdapter(LogPrestamoCarritoJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<LogPrestamoCarritoModel> buscarPorId(Long id) {
        return repository.findById(id).map(LogPrestamoCarritoMapper::toModel);
    }

    @Override
    public LogPrestamoCarritoModel guardar(LogPrestamoCarritoModel modelo) {
        return LogPrestamoCarritoMapper.toModel(repository.save(LogPrestamoCarritoMapper.toEntity(modelo)));
    }

    @Override
    public void eliminarPorId(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Page<LogPrestamoCarritoModel> buscarPorCondominio(
            Long idCondominio, Long userId, Instant fechaInicio, Instant fechaFin, Pageable pageable) {
        var inicio = fechaInicio != null ? LocalDateTime.ofInstant(fechaInicio, ZoneOffset.UTC) : null;
        var fin = fechaFin != null ? LocalDateTime.ofInstant(fechaFin, ZoneOffset.UTC) : null;
        return repository.findByFilters(idCondominio, userId, inicio, fin, pageable)
                .map(LogPrestamoCarritoMapper::toModel);
    }
}

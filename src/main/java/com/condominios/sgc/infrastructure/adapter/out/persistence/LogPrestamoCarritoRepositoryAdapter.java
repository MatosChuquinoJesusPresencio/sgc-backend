package com.condominios.sgc.infrastructure.adapter.out.persistence;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import com.condominios.sgc.application.dto.query.PaginaQuery;
import com.condominios.sgc.application.dto.result.PaginaResult;
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
    public PaginaResult<LogPrestamoCarritoModel> buscarPorCondominio(
            Long idCondominio, Long userId, Instant fechaInicio, Instant fechaFin, PaginaQuery paginacion) {
        var pageable = PageRequest.of(paginacion.pagina(), paginacion.tamano());
        var inicio = fechaInicio != null ? LocalDateTime.ofInstant(fechaInicio, ZoneOffset.UTC) : null;
        var fin = fechaFin != null ? LocalDateTime.ofInstant(fechaFin, ZoneOffset.UTC) : null;
        var page = repository.findByFilters(idCondominio, userId, inicio, fin, pageable)
                .map(LogPrestamoCarritoMapper::toModel);
        return new PaginaResult<>(page.getContent(), page.getTotalElements(), page.getNumber(), page.getSize());
    }

    @Override
    public List<LogPrestamoCarritoModel> buscarActivosPorCondominio(Long idCondominio) {
        return repository.findByIdCondominioAndFechaDevolucionIsNullOrderByFechaPrestamoDesc(idCondominio)
                .stream()
                .map(LogPrestamoCarritoMapper::toModel)
                .toList();
    }
}

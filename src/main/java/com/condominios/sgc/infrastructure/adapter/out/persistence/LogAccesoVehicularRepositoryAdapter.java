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
    public PaginaResult<LogAccesoVehicularModel> buscarPorCondominio(
            Long idCondominio, Long userId, Instant fechaInicio, Instant fechaFin, PaginaQuery paginacion) {
        var pageable = PageRequest.of(paginacion.pagina(), paginacion.tamano());
        var inicio = fechaInicio != null ? LocalDateTime.ofInstant(fechaInicio, ZoneOffset.UTC) : null;
        var fin = fechaFin != null ? LocalDateTime.ofInstant(fechaFin, ZoneOffset.UTC) : null;
        var page = repository.findByFilters(idCondominio, userId, inicio, fin, pageable)
                .map(LogAccesoVehicularMapper::toModel);
        return new PaginaResult<>(page.getContent(), page.getTotalElements(), page.getNumber(), page.getSize());
    }

    @Override
    public List<LogAccesoVehicularModel> buscarRecientesPorCondominio(Long idCondominio, int limit) {
        return repository.findRecentByCondominio(idCondominio, PageRequest.of(0, limit))
                .stream()
                .map(LogAccesoVehicularMapper::toModel)
                .toList();
    }

    @Override
    public List<Long> buscarIdVehiculosActivosPorEstacionamiento(Long idEstacionamiento) {
        return repository.findActiveVehicleIdsByEstacionamiento(idEstacionamiento);
    }
}

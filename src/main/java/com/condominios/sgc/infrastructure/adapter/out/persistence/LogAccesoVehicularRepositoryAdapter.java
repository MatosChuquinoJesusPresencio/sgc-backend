package com.condominios.sgc.infrastructure.adapter.out.persistence;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

import com.condominios.sgc.application.dto.result.AdminLogEntryResult;
import com.condominios.sgc.infrastructure.adapter.out.persistence.repository.LogCombinadoProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    @Override
    public List<LogAccesoVehicularModel> buscarRecientesPorCondominio(Long idCondominio, int limit) {
        return repository.findRecentByCondominio(idCondominio, PageRequest.of(0, limit))
                .stream()
                .map(LogAccesoVehicularMapper::toModel)
                .toList();
    }
    @Override
    public Page<AdminLogEntryResult> buscarHistorialCombinado(Long condominioId, int pagina, int tamano) {
        PageRequest pageRequest = PageRequest.of(pagina, tamano);
        Page<LogCombinadoProjection> proyeccionPage = repository.buscarHistorialCombinadoPaginado(condominioId, pageRequest);

        return proyeccionPage.map(p ->
                new AdminLogEntryResult(
                        p.getId(),
                        p.getTipoLog(),
                        p.getPlaca(),
                        p.getOcupante(),
                        p.getDatosInquilino(),
                        p.getMetodo(),
                        p.getFechaEntrada() != null ? p.getFechaEntrada().toString() : null,
                        p.getFechaSalida() != null ? p.getFechaSalida().toString() : null,
                        p.getSolicitante(),
                        p.getNombreSolicitante(),
                        p.getDniSolicitante(),
                        p.getPenalizacion(),
                        p.getFechaPrestamo() != null ? p.getFechaPrestamo().toString() : null,
                        p.getFechaDevolucion() != null ? p.getFechaDevolucion().toString() : null,
                        p.getCondominioId()
                )
        );
    }
}

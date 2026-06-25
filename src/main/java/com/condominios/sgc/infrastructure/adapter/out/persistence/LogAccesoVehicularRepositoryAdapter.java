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
        // Creamos la petición de paginación
        PageRequest pageRequest = PageRequest.of(pagina, tamano);

        // Llamamos al Native Query
        Page<LogCombinadoProjection> proyeccionPage = repository.buscarHistorialCombinadoPaginado(condominioId, pageRequest);

        // Mapeamos el resultado SQL a tu DTO (AdminLogEntryResult)
        return proyeccionPage.map(proyeccion ->
                new AdminLogEntryResult(
                        proyeccion.getTipoLog(),
                        proyeccion.getIdentificador(),
                        proyeccion.getUsuario(),
                        proyeccion.getFecha().toString() // *Nota: Si tu DTO pide la fecha en String, pon proyeccion.getFecha().toString()
                )
        );
    }
}

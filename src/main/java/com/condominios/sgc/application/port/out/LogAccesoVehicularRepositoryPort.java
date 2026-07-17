package com.condominios.sgc.application.port.out;

import java.time.Instant;
import java.util.Optional;

import java.util.List;

import com.condominios.sgc.application.dto.query.PaginaQuery;
import com.condominios.sgc.application.dto.result.PaginaResult;
import com.condominios.sgc.domain.model.LogAccesoVehicularModel;

public interface LogAccesoVehicularRepositoryPort {
    Optional<LogAccesoVehicularModel> buscarPorId(Long id);
    LogAccesoVehicularModel guardar(LogAccesoVehicularModel modelo);
    void eliminarPorId(Long id);
    PaginaResult<LogAccesoVehicularModel> buscarPorCondominio(Long idCondominio, Long userId, Instant fechaInicio, Instant fechaFin, PaginaQuery paginacion);
    List<LogAccesoVehicularModel> buscarRecientesPorCondominio(Long idCondominio, int limit);
    List<Long> buscarIdVehiculosActivosPorEstacionamiento(Long idEstacionamiento);
}

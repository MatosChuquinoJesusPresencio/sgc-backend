package com.condominios.sgc.application.port.out;

import java.time.Instant;
import java.util.Optional;

import com.condominios.sgc.application.dto.result.AdminLogEntryResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

import com.condominios.sgc.domain.model.LogAccesoVehicularModel;

public interface LogAccesoVehicularRepositoryPort {
    Optional<LogAccesoVehicularModel> buscarPorId(Long id);
    LogAccesoVehicularModel guardar(LogAccesoVehicularModel modelo);
    void eliminarPorId(Long id);
    Page<LogAccesoVehicularModel> buscarPorCondominio(Long idCondominio, Long userId, Instant fechaInicio, Instant fechaFin, Pageable pageable);
    List<LogAccesoVehicularModel> buscarRecientesPorCondominio(Long idCondominio, int limit);
    Page<AdminLogEntryResult> buscarHistorialCombinado(Long condominioId, int pagina, int tamano);
}

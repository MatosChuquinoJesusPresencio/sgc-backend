package com.condominios.sgc.domain.port;

import java.util.Optional;

import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.LogAccesoVehicularModel;

public interface LogAccesoVehicularPort {
    Optional<LogAccesoVehicularModel> findById(Long id);
    PaginacionResponse<LogAccesoVehicularModel> findByCondominioId(Long condominioId, PaginacionRequest request);
    PaginacionResponse<LogAccesoVehicularModel> findByApartamentoId(Long apartamentoId, PaginacionRequest request);
    LogAccesoVehicularModel save(LogAccesoVehicularModel log);
}

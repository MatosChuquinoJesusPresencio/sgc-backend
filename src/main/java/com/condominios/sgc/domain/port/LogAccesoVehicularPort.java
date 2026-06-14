package com.condominios.sgc.domain.port;

import com.condominios.sgc.domain.dto.request.PaginacionRequest;
import com.condominios.sgc.domain.dto.response.PaginacionResponse;
import com.condominios.sgc.domain.filter.LogAccesoVehicularFilter;
import com.condominios.sgc.domain.model.LogAccesoVehicularModel;
import java.util.Optional;

public interface LogAccesoVehicularPort {
    LogAccesoVehicularModel guardar(LogAccesoVehicularModel log);
    Optional<LogAccesoVehicularModel> obtenerPorId(Long id);
    PaginacionResponse<LogAccesoVehicularModel> obtenerTodos(PaginacionRequest request, LogAccesoVehicularFilter filtro);
}


package com.condominios.sgc.application.usecase;

import com.condominios.sgc.application.dto.query.ListarLogsAccesoVehicularQuery;
import com.condominios.sgc.application.dto.response.LogAccesoVehicularResponse;
import com.condominios.sgc.domain.dto.response.PaginacionResponse;

public interface ListarLogsAccesoVehicularUseCase {
    PaginacionResponse<LogAccesoVehicularResponse> ejecutar(ListarLogsAccesoVehicularQuery query);
}

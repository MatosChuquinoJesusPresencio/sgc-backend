package com.condominios.sgc.application.usecase.estacionamiento;

import com.condominios.sgc.application.dto.query.ListarEstacionamientosQuery;
import com.condominios.sgc.application.dto.response.EstacionamientoResponse;
import com.condominios.sgc.domain.dto.response.PaginacionResponse;

public interface ListarEstacionamientosUseCase {
    PaginacionResponse<EstacionamientoResponse> ejecutar(ListarEstacionamientosQuery query);
}

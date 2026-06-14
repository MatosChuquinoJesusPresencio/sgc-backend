package com.condominios.sgc.application.usecase.vehiculo;

import com.condominios.sgc.application.dto.query.ListarVehiculosQuery;
import com.condominios.sgc.application.dto.response.VehiculoResponse;
import com.condominios.sgc.domain.dto.response.PaginacionResponse;

public interface ListarVehiculosUseCase {
    PaginacionResponse<VehiculoResponse> ejecutar(ListarVehiculosQuery query);
}

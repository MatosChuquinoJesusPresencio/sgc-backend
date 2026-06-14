package com.condominios.sgc.application.usecase.carrito;

import com.condominios.sgc.application.dto.query.ListarCarritosQuery;
import com.condominios.sgc.application.dto.response.CarritoResponse;
import com.condominios.sgc.domain.dto.response.PaginacionResponse;

public interface ListarCarritosUseCase {
    PaginacionResponse<CarritoResponse> ejecutar(ListarCarritosQuery query);
}

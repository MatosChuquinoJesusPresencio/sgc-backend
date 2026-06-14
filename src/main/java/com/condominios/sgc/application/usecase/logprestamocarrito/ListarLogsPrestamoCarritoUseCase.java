package com.condominios.sgc.application.usecase.logprestamocarrito;

import com.condominios.sgc.application.dto.query.ListarLogsPrestamoCarritoQuery;
import com.condominios.sgc.application.dto.response.LogPrestamoCarritoResponse;
import com.condominios.sgc.domain.dto.response.PaginacionResponse;

public interface ListarLogsPrestamoCarritoUseCase {
    PaginacionResponse<LogPrestamoCarritoResponse> ejecutar(ListarLogsPrestamoCarritoQuery query);
}

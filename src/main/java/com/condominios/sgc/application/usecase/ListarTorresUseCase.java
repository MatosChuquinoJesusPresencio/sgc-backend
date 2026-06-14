package com.condominios.sgc.application.usecase;

import com.condominios.sgc.application.dto.query.ListarTorresQuery;
import com.condominios.sgc.application.dto.response.TorreResponse;
import com.condominios.sgc.domain.dto.response.PaginacionResponse;

public interface ListarTorresUseCase {
    PaginacionResponse<TorreResponse> ejecutar(ListarTorresQuery query);
}

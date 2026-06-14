package com.condominios.sgc.application.usecase.piso;

import com.condominios.sgc.application.dto.query.ListarPisosQuery;
import com.condominios.sgc.application.dto.response.PisoResponse;
import com.condominios.sgc.domain.dto.response.PaginacionResponse;

public interface ListarPisosUseCase {
    PaginacionResponse<PisoResponse> ejecutar(ListarPisosQuery query);
}

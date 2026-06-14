package com.condominios.sgc.application.usecase;

import com.condominios.sgc.application.dto.query.ListarCondominiosQuery;
import com.condominios.sgc.application.dto.response.CondominioResponse;
import com.condominios.sgc.domain.dto.response.PaginacionResponse;

public interface ListarCondominiosUseCase {
    PaginacionResponse<CondominioResponse> ejecutar(ListarCondominiosQuery query);
}

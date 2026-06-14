package com.condominios.sgc.application.usecase;

import com.condominios.sgc.application.dto.query.ListarInquilinosQuery;
import com.condominios.sgc.application.dto.response.InquilinoResponse;
import com.condominios.sgc.domain.dto.response.PaginacionResponse;

public interface ListarInquilinosUseCase {
    PaginacionResponse<InquilinoResponse> ejecutar(ListarInquilinosQuery query);
}

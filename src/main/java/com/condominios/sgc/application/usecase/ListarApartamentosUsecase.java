package com.condominios.sgc.application.usecase;

import com.condominios.sgc.application.dto.query.ListarApartamentosQuery;
import com.condominios.sgc.application.dto.response.ApartamentoResponse;
import com.condominios.sgc.domain.dto.response.PaginacionResponse;

public interface ListarApartamentosUsecase {
    PaginacionResponse<ApartamentoResponse> ejecutar(ListarApartamentosQuery query);
}

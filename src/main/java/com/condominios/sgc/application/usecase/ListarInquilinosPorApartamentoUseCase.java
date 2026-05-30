package com.condominios.sgc.application.usecase;

import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.InquilinoModel;

public interface ListarInquilinosPorApartamentoUseCase {
    PaginacionResponse<InquilinoModel> ejecutar(Long apartamentoId, PaginacionRequest request);
}

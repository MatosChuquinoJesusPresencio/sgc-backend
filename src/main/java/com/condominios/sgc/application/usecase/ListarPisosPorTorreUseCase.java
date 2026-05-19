package com.condominios.sgc.application.usecase;

import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.PisoModel;

public interface ListarPisosPorTorreUseCase {
    PaginacionResponse<PisoModel> ejecutar(Long torreId, PaginacionRequest request);
}
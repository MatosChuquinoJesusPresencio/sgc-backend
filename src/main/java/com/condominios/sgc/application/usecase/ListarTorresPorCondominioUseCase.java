package com.condominios.sgc.application.usecase;

import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.TorreModel;

public interface ListarTorresPorCondominioUseCase {
    PaginacionResponse<TorreModel> ejecutar(Long condominioId, PaginacionRequest request);
}

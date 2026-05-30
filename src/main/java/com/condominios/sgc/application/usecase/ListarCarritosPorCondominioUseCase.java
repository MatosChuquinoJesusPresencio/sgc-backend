package com.condominios.sgc.application.usecase;

import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.CarritoModel;

public interface ListarCarritosPorCondominioUseCase {
    PaginacionResponse<CarritoModel> ejecutar(Long condominioId, PaginacionRequest request);
}

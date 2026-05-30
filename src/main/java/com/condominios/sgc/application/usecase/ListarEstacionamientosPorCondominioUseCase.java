package com.condominios.sgc.application.usecase;

import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.EstacionamientoModel;

public interface ListarEstacionamientosPorCondominioUseCase {
    PaginacionResponse<EstacionamientoModel> ejecutar(Long condominioId, PaginacionRequest request);
}

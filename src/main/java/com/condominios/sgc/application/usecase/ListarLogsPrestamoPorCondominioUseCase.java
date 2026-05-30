package com.condominios.sgc.application.usecase;

import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.LogPrestamoCarritoModel;

public interface ListarLogsPrestamoPorCondominioUseCase {
    PaginacionResponse<LogPrestamoCarritoModel> ejecutar(Long condominioId, PaginacionRequest request);
}

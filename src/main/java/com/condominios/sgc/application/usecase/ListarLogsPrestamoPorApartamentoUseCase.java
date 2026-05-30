package com.condominios.sgc.application.usecase;

import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.LogPrestamoCarritoModel;

public interface ListarLogsPrestamoPorApartamentoUseCase {
    PaginacionResponse<LogPrestamoCarritoModel> ejecutar(Long apartamentoId, PaginacionRequest request);
}

package com.condominios.sgc.application.usecase;

import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.VehiculoModel;

public interface ListarVehiculosUseCase {
    PaginacionResponse<VehiculoModel> ejecutar(PaginacionRequest request);
}

package com.condominios.sgc.application.usecase;

import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.CondominioModel;

public interface ListarCondominiosUseCase {
    PaginacionResponse<CondominioModel> ejecutar(PaginacionRequest request);
}

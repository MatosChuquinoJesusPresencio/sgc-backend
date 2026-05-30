package com.condominios.sgc.application.usecase;

import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.LogAccesoVehicularModel;

public interface ListarLogsAccesoPorCondominioUseCase {
    PaginacionResponse<LogAccesoVehicularModel> ejecutar(Long condominioId, PaginacionRequest request);
}

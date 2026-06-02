package com.condominios.sgc.application.usecase;

import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.LogAccesoVehicularModel;

public interface ListarLogsAccesoPorApartamentoUseCase {
    PaginacionResponse<LogAccesoVehicularModel> ejecutar(Long apartamentoId, PaginacionRequest request);
}

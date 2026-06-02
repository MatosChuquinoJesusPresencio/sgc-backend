package com.condominios.sgc.application.usecase;
import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.ApartamentoModel;

public interface ListarApartamentosPorPisoUseCase {
    PaginacionResponse<ApartamentoModel> ejecutar(Long pisoId, PaginacionRequest request);
}
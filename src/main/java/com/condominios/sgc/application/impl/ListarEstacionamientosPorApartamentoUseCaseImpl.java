package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.usecase.ListarEstacionamientosPorApartamentoUseCase;
import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.EstacionamientoModel;
import com.condominios.sgc.domain.port.EstacionamientoPort;

public class ListarEstacionamientosPorApartamentoUseCaseImpl implements ListarEstacionamientosPorApartamentoUseCase {

    private final EstacionamientoPort estacionamientoPort;

    public ListarEstacionamientosPorApartamentoUseCaseImpl(EstacionamientoPort estacionamientoPort) {
        this.estacionamientoPort = estacionamientoPort;
    }

    @Override
    public PaginacionResponse<EstacionamientoModel> ejecutar(Long apartamentoId, PaginacionRequest request) {
        return estacionamientoPort.findByApartamentoId(apartamentoId, request);
    }
}

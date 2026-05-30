package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.usecase.ListarEstacionamientosPorCondominioUseCase;
import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.EstacionamientoModel;
import com.condominios.sgc.domain.port.EstacionamientoPort;

public class ListarEstacionamientosPorCondominioUseCaseImpl implements ListarEstacionamientosPorCondominioUseCase {

    private final EstacionamientoPort estacionamientoPort;

    public ListarEstacionamientosPorCondominioUseCaseImpl(EstacionamientoPort estacionamientoPort) {
        this.estacionamientoPort = estacionamientoPort;
    }

    @Override
    public PaginacionResponse<EstacionamientoModel> ejecutar(Long condominioId, PaginacionRequest request) {
        return estacionamientoPort.findByCondominioId(condominioId, request);
    }
}

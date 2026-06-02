package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.usecase.ListarCarritosPorCondominioUseCase;
import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.CarritoModel;
import com.condominios.sgc.domain.port.CarritoPort;

public class ListarCarritosPorCondominioUseCaseImpl implements ListarCarritosPorCondominioUseCase {

    private final CarritoPort carritoPort;

    public ListarCarritosPorCondominioUseCaseImpl(CarritoPort carritoPort) {
        this.carritoPort = carritoPort;
    }

    @Override
    public PaginacionResponse<CarritoModel> ejecutar(Long condominioId, PaginacionRequest request) {
        return carritoPort.findByCondominioId(condominioId, request);
    }
}

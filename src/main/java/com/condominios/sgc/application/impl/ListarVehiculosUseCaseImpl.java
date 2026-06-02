package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.usecase.ListarVehiculosUseCase;
import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.VehiculoModel;
import com.condominios.sgc.domain.port.VehiculoPort;

public class ListarVehiculosUseCaseImpl implements ListarVehiculosUseCase {

    private final VehiculoPort vehiculoPort;

    public ListarVehiculosUseCaseImpl(VehiculoPort vehiculoPort) {
        this.vehiculoPort = vehiculoPort;
    }

    @Override
    public PaginacionResponse<VehiculoModel> ejecutar(PaginacionRequest request) {
        return vehiculoPort.findAll(request);
    }
}

package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.usecase.ObtenerCarritoUseCase;
import com.condominios.sgc.domain.exception.CarritoException;
import com.condominios.sgc.domain.model.CarritoModel;
import com.condominios.sgc.domain.port.CarritoPort;

public class ObtenerCarritoUseCaseImpl implements ObtenerCarritoUseCase {

    private final CarritoPort carritoPort;

    public ObtenerCarritoUseCaseImpl(CarritoPort carritoPort) {
        this.carritoPort = carritoPort;
    }

    @Override
    public CarritoModel ejecutar(Long id) {
        return carritoPort.findById(id)
                .orElseThrow(() -> CarritoException.noEncontrado(id));
    }
}

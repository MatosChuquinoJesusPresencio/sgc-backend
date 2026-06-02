package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.usecase.EliminarCarritoUseCase;
import com.condominios.sgc.domain.exception.CarritoException;
import com.condominios.sgc.domain.port.CarritoPort;

public class EliminarCarritoUseCaseImpl implements EliminarCarritoUseCase {

    private final CarritoPort carritoPort;

    public EliminarCarritoUseCaseImpl(CarritoPort carritoPort) {
        this.carritoPort = carritoPort;
    }

    @Override
    public void ejecutar(Long id) {
        carritoPort.findById(id)
                .orElseThrow(() -> CarritoException.noEncontrado(id));

        carritoPort.deleteById(id);
    }
}

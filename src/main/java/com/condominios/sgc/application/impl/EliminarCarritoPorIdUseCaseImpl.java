package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.usecase.EliminarCarritoPorIdUseCase;
import com.condominios.sgc.domain.exception.CarritoException;
import com.condominios.sgc.domain.port.CarritoPort;

public class EliminarCarritoPorIdUseCaseImpl implements EliminarCarritoPorIdUseCase {
    private final CarritoPort carritoPort;

    public EliminarCarritoPorIdUseCaseImpl(CarritoPort carritoPort) {
        this.carritoPort = carritoPort;
    }

    @Override
    public void ejecutar(Long id) {
        carritoPort.obtenerPorId(id)
            .orElseThrow(CarritoException::noEncontrado);

        carritoPort.eliminarPorId(id);
    }
}

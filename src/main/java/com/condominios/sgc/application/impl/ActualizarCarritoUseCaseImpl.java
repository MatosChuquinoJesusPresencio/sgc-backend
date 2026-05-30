package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.dto.ActualizarCarritoRequest;
import com.condominios.sgc.application.usecase.ActualizarCarritoUseCase;
import com.condominios.sgc.domain.exception.CarritoException;
import com.condominios.sgc.domain.model.CarritoModel;
import com.condominios.sgc.domain.port.CarritoPort;

public class ActualizarCarritoUseCaseImpl implements ActualizarCarritoUseCase {

    private final CarritoPort carritoPort;

    public ActualizarCarritoUseCaseImpl(CarritoPort carritoPort) {
        this.carritoPort = carritoPort;
    }

    @Override
    public CarritoModel ejecutar(Long id, ActualizarCarritoRequest request) {
        CarritoModel carrito = carritoPort.findById(id)
                .orElseThrow(() -> CarritoException.noEncontrado(id));

        carrito.actualizarDatos(request.codigo());

        return carritoPort.save(carrito);
    }
}

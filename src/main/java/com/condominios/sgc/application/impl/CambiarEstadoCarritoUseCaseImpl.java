package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.usecase.CambiarEstadoCarritoUseCase;
import com.condominios.sgc.domain.auxiliar.EstadoCarrito;
import com.condominios.sgc.domain.exception.CarritoException;
import com.condominios.sgc.domain.model.CarritoModel;
import com.condominios.sgc.domain.port.CarritoPort;

public class CambiarEstadoCarritoUseCaseImpl implements CambiarEstadoCarritoUseCase {

    private final CarritoPort carritoPort;

    public CambiarEstadoCarritoUseCaseImpl(CarritoPort carritoPort) {
        this.carritoPort = carritoPort;
    }

    @Override
    public CarritoModel ejecutar(Long id, EstadoCarrito nuevoEstado) {
        CarritoModel carrito = carritoPort.findById(id)
                .orElseThrow(() -> CarritoException.noEncontrado(id));

        carrito.cambiarEstado(nuevoEstado);

        return carritoPort.save(carrito);
    }
}

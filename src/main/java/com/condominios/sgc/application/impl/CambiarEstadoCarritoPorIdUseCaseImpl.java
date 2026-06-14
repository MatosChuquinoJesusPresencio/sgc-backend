package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.dto.response.CarritoResponse;
import com.condominios.sgc.application.usecase.CambiarEstadoCarritoPorIdUseCase;
import com.condominios.sgc.domain.auxiliar.EstadoCarrito;
import com.condominios.sgc.domain.exception.CarritoException;
import com.condominios.sgc.domain.model.CarritoModel;
import com.condominios.sgc.domain.port.CarritoPort;

public class CambiarEstadoCarritoPorIdUseCaseImpl implements CambiarEstadoCarritoPorIdUseCase {
    private final CarritoPort carritoPort;

    public CambiarEstadoCarritoPorIdUseCaseImpl(CarritoPort carritoPort) {
        this.carritoPort = carritoPort;
    }

    @Override
    public CarritoResponse ejecutar(Long id, EstadoCarrito estado) {
        CarritoModel carrito = carritoPort.obtenerPorId(id)
            .orElseThrow(CarritoException::noEncontrado);

        carrito.actualizarEstado(estado);
        carrito = carritoPort.guardar(carrito);

        return CarritoResponse.desdeModelo(carrito);
    }
}

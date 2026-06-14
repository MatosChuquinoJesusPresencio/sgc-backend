package com.condominios.sgc.application.impl.carrito;

import com.condominios.sgc.application.dto.response.CarritoResponse;
import com.condominios.sgc.application.usecase.carrito.ActualizarCarritoPorIdUseCase;
import com.condominios.sgc.domain.exception.CarritoException;
import com.condominios.sgc.domain.model.CarritoModel;
import com.condominios.sgc.domain.port.CarritoPort;

public class ActualizarCarritoPorIdUseCaseImpl implements ActualizarCarritoPorIdUseCase {
    private final CarritoPort carritoPort;

    public ActualizarCarritoPorIdUseCaseImpl(CarritoPort carritoPort) {
        this.carritoPort = carritoPort;
    }

    @Override
    public CarritoResponse ejecutar(Long id, String codigo) {
        CarritoModel carrito = carritoPort.obtenerPorId(id)
            .orElseThrow(CarritoException::noEncontrado);

        carrito.actualizarCodigo(codigo);
        carrito = carritoPort.guardar(carrito);

        return CarritoResponse.desdeModelo(carrito);
    }
}

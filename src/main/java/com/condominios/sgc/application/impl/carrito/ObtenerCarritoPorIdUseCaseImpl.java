package com.condominios.sgc.application.impl.carrito;

import com.condominios.sgc.application.dto.response.CarritoResponse;
import com.condominios.sgc.application.usecase.carrito.ObtenerCarritoPorIdUseCase;
import com.condominios.sgc.domain.exception.CarritoException;
import com.condominios.sgc.domain.port.CarritoPort;

public class ObtenerCarritoPorIdUseCaseImpl implements ObtenerCarritoPorIdUseCase {
    private final CarritoPort carritoPort;

    public ObtenerCarritoPorIdUseCaseImpl(CarritoPort carritoPort) {
        this.carritoPort = carritoPort;
    }

    @Override
    public CarritoResponse ejecutar(Long id) {
        return carritoPort.obtenerPorId(id)
            .map(CarritoResponse::desdeModelo)
            .orElseThrow(CarritoException::noEncontrado);
    }
}

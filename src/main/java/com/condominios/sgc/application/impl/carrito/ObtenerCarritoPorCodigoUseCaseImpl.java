package com.condominios.sgc.application.impl.carrito;

import com.condominios.sgc.application.dto.response.CarritoResponse;
import com.condominios.sgc.application.usecase.carrito.ObtenerCarritoPorCodigoUseCase;
import com.condominios.sgc.domain.exception.CarritoException;
import com.condominios.sgc.domain.port.CarritoPort;

public class ObtenerCarritoPorCodigoUseCaseImpl implements ObtenerCarritoPorCodigoUseCase {
    private final CarritoPort carritoPort;

    public ObtenerCarritoPorCodigoUseCaseImpl(CarritoPort carritoPort) {
        this.carritoPort = carritoPort;
    }

    @Override
    public CarritoResponse ejecutar(String codigo) {
        return carritoPort.obtenerPorCodigo(codigo)
            .map(CarritoResponse::desdeModelo)
            .orElseThrow(CarritoException::noEncontrado);
    }
}

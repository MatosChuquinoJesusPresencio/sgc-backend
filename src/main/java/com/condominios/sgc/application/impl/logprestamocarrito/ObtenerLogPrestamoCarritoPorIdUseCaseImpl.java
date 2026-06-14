package com.condominios.sgc.application.impl.logprestamocarrito;

import com.condominios.sgc.application.dto.response.LogPrestamoCarritoResponse;
import com.condominios.sgc.application.usecase.logprestamocarrito.ObtenerLogPrestamoCarritoPorIdUseCase;
import com.condominios.sgc.domain.exception.LogPrestamoCarritoException;
import com.condominios.sgc.domain.port.LogPrestamoCarritoPort;

public class ObtenerLogPrestamoCarritoPorIdUseCaseImpl implements ObtenerLogPrestamoCarritoPorIdUseCase {
    private final LogPrestamoCarritoPort logPrestamoCarritoPort;

    public ObtenerLogPrestamoCarritoPorIdUseCaseImpl(LogPrestamoCarritoPort logPrestamoCarritoPort) {
        this.logPrestamoCarritoPort = logPrestamoCarritoPort;
    }

    @Override
    public LogPrestamoCarritoResponse ejecutar(Long id) {
        return logPrestamoCarritoPort.obtenerPorId(id)
            .map(LogPrestamoCarritoResponse::desdeModelo)
            .orElseThrow(LogPrestamoCarritoException::noEncontrado);
    }
}

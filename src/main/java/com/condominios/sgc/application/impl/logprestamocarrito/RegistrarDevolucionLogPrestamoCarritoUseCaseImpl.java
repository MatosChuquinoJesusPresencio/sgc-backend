package com.condominios.sgc.application.impl.logprestamocarrito;

import java.math.BigDecimal;

import com.condominios.sgc.application.dto.response.LogPrestamoCarritoResponse;
import com.condominios.sgc.application.usecase.logprestamocarrito.RegistrarDevolucionLogPrestamoCarritoUseCase;
import com.condominios.sgc.domain.exception.LogPrestamoCarritoException;
import com.condominios.sgc.domain.model.LogPrestamoCarritoModel;
import com.condominios.sgc.domain.port.LogPrestamoCarritoPort;

public class RegistrarDevolucionLogPrestamoCarritoUseCaseImpl implements RegistrarDevolucionLogPrestamoCarritoUseCase {
    private final LogPrestamoCarritoPort logPrestamoCarritoPort;

    public RegistrarDevolucionLogPrestamoCarritoUseCaseImpl(LogPrestamoCarritoPort logPrestamoCarritoPort) {
        this.logPrestamoCarritoPort = logPrestamoCarritoPort;
    }

    @Override
    public LogPrestamoCarritoResponse ejecutar(Long id, BigDecimal penalizacion) {
        LogPrestamoCarritoModel log = logPrestamoCarritoPort.obtenerPorId(id)
            .orElseThrow(LogPrestamoCarritoException::noEncontrado);

        log.registrarDevolucion(penalizacion);

        log = logPrestamoCarritoPort.guardar(log);

        return LogPrestamoCarritoResponse.desdeModelo(log);
    }
}

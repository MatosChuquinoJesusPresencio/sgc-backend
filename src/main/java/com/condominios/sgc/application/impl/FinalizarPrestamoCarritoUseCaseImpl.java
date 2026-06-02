package com.condominios.sgc.application.impl;

import java.math.BigDecimal;

import com.condominios.sgc.application.usecase.FinalizarPrestamoCarritoUseCase;
import com.condominios.sgc.domain.exception.LogPrestamoCarritoException;
import com.condominios.sgc.domain.model.LogPrestamoCarritoModel;
import com.condominios.sgc.domain.port.LogPrestamoCarritoPort;

public class FinalizarPrestamoCarritoUseCaseImpl implements FinalizarPrestamoCarritoUseCase {

    private final LogPrestamoCarritoPort logPrestamoCarritoPort;

    public FinalizarPrestamoCarritoUseCaseImpl(LogPrestamoCarritoPort logPrestamoCarritoPort) {
        this.logPrestamoCarritoPort = logPrestamoCarritoPort;
    }

    @Override
    public LogPrestamoCarritoModel ejecutar(Long logId, BigDecimal penalizacion) {
        LogPrestamoCarritoModel log = logPrestamoCarritoPort.findById(logId)
                .orElseThrow(() -> LogPrestamoCarritoException.noEncontrado(logId));

        log.registrarDevolucion(penalizacion);

        return logPrestamoCarritoPort.save(log);
    }
}

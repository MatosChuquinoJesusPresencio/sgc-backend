package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.usecase.ObtenerLogPrestamoUseCase;
import com.condominios.sgc.domain.exception.LogPrestamoCarritoException;
import com.condominios.sgc.domain.model.LogPrestamoCarritoModel;
import com.condominios.sgc.domain.port.LogPrestamoCarritoPort;

public class ObtenerLogPrestamoUseCaseImpl implements ObtenerLogPrestamoUseCase {

    private final LogPrestamoCarritoPort logPrestamoCarritoPort;

    public ObtenerLogPrestamoUseCaseImpl(LogPrestamoCarritoPort logPrestamoCarritoPort) {
        this.logPrestamoCarritoPort = logPrestamoCarritoPort;
    }

    @Override
    public LogPrestamoCarritoModel ejecutar(Long id) {
        return logPrestamoCarritoPort.findById(id)
                .orElseThrow(() -> LogPrestamoCarritoException.noEncontrado(id));
    }
}

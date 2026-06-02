package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.dto.IniciarPrestamoRequest;
import com.condominios.sgc.application.usecase.IniciarPrestamoCarritoUseCase;
import com.condominios.sgc.domain.model.LogPrestamoCarritoModel;
import com.condominios.sgc.domain.port.LogPrestamoCarritoPort;

public class IniciarPrestamoCarritoUseCaseImpl implements IniciarPrestamoCarritoUseCase {

    private final LogPrestamoCarritoPort logPrestamoCarritoPort;

    public IniciarPrestamoCarritoUseCaseImpl(LogPrestamoCarritoPort logPrestamoCarritoPort) {
        this.logPrestamoCarritoPort = logPrestamoCarritoPort;
    }

    @Override
    public LogPrestamoCarritoModel ejecutar(IniciarPrestamoRequest request) {
        LogPrestamoCarritoModel modelo = new LogPrestamoCarritoModel(
                request.solicitante(),
                request.nombreSolicitante(),
                request.dniSolicitante(),
                request.apartamentoId(),
                request.carritoId());

        return logPrestamoCarritoPort.save(modelo);
    }
}

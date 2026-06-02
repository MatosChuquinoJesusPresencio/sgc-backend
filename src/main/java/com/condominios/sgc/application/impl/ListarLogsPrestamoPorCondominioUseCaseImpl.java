package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.usecase.ListarLogsPrestamoPorCondominioUseCase;
import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.LogPrestamoCarritoModel;
import com.condominios.sgc.domain.port.LogPrestamoCarritoPort;

public class ListarLogsPrestamoPorCondominioUseCaseImpl implements ListarLogsPrestamoPorCondominioUseCase {

    private final LogPrestamoCarritoPort logPrestamoCarritoPort;

    public ListarLogsPrestamoPorCondominioUseCaseImpl(LogPrestamoCarritoPort logPrestamoCarritoPort) {
        this.logPrestamoCarritoPort = logPrestamoCarritoPort;
    }

    @Override
    public PaginacionResponse<LogPrestamoCarritoModel> ejecutar(Long condominioId, PaginacionRequest request) {
        return logPrestamoCarritoPort.findByCondominioId(condominioId, request);
    }
}

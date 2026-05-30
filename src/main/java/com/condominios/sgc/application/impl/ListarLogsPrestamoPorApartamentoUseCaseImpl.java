package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.usecase.ListarLogsPrestamoPorApartamentoUseCase;
import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.LogPrestamoCarritoModel;
import com.condominios.sgc.domain.port.LogPrestamoCarritoPort;

public class ListarLogsPrestamoPorApartamentoUseCaseImpl implements ListarLogsPrestamoPorApartamentoUseCase {

    private final LogPrestamoCarritoPort logPrestamoCarritoPort;

    public ListarLogsPrestamoPorApartamentoUseCaseImpl(LogPrestamoCarritoPort logPrestamoCarritoPort) {
        this.logPrestamoCarritoPort = logPrestamoCarritoPort;
    }

    @Override
    public PaginacionResponse<LogPrestamoCarritoModel> ejecutar(Long apartamentoId, PaginacionRequest request) {
        return logPrestamoCarritoPort.findByApartamentoId(apartamentoId, request);
    }
}

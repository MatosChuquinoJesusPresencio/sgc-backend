package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.usecase.ListarApartamentosPorPisoUseCase;
import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.exception.PisoException;
import com.condominios.sgc.domain.model.ApartamentoModel;
import com.condominios.sgc.domain.port.ApartamentoPort;
import com.condominios.sgc.domain.port.PisoPort;

public class ListarApartamentosPorPisoUseCaseImpl implements ListarApartamentosPorPisoUseCase {
    private final ApartamentoPort apartamentoPort;
    private final PisoPort pisoPort;

    public ListarApartamentosPorPisoUseCaseImpl(ApartamentoPort apartamentoPort, PisoPort pisoPort) {
        this.apartamentoPort = apartamentoPort;
        this.pisoPort = pisoPort;
    }

    @Override
    public PaginacionResponse<ApartamentoModel> ejecutar(Long pisoId, PaginacionRequest request) {
        if (pisoPort.findById(pisoId) == null) {
            throw PisoException.noEncontrado(pisoId);
        }
        return apartamentoPort.findByPisoId(pisoId, request);
    }
}
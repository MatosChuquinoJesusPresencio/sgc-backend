package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.usecase.ListarPisosPorTorreUseCase;
import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.exception.TorreException;
import com.condominios.sgc.domain.model.PisoModel;
import com.condominios.sgc.domain.port.PisoPort;
import com.condominios.sgc.domain.port.TorrePort;

public class ListarPisosPorTorreUseCaseImpl implements ListarPisosPorTorreUseCase {
    private final PisoPort pisoPort;
    private final TorrePort torrePort;

    public ListarPisosPorTorreUseCaseImpl(PisoPort pisoPort, TorrePort torrePort) {
        this.pisoPort = pisoPort;
        this.torrePort = torrePort;
    }

    @Override
    public PaginacionResponse<PisoModel> ejecutar(Long torreId, PaginacionRequest request) {
        if (torrePort.findById(torreId) == null) {
            throw TorreException.noEncontrada(torreId);
        }
        return pisoPort.findByTorreId(torreId, request);
    }
}
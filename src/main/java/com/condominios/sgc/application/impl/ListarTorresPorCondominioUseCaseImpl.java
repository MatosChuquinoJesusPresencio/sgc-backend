package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.usecase.ListarTorresPorCondominioUseCase;
import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.exception.CondominioException;
import com.condominios.sgc.domain.model.TorreModel;
import com.condominios.sgc.domain.port.CondominioPort;
import com.condominios.sgc.domain.port.TorrePort;

public class ListarTorresPorCondominioUseCaseImpl implements ListarTorresPorCondominioUseCase {
    private final TorrePort torrePort;
    private final CondominioPort condominioPort;

    public ListarTorresPorCondominioUseCaseImpl(TorrePort torrePort, CondominioPort condominioPort) {
        this.torrePort = torrePort;
        this.condominioPort = condominioPort;
    }

    @Override
    public PaginacionResponse<TorreModel> ejecutar(Long condominioId, PaginacionRequest request) {
        condominioPort.findById(condominioId)
            .orElseThrow(() -> CondominioException.noEncontrado(condominioId));
        return torrePort.findByCondominioId(condominioId, request);
    }
}
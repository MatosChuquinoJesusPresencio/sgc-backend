package com.condominios.sgc.application.impl;

import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.exception.CondominioException;
import com.condominios.sgc.domain.model.TorreModel;
import com.condominios.sgc.domain.port.CondominioPort;
import com.condominios.sgc.domain.port.TorrePort;

public class ListarTorresPorCondominioUseCaseImpl {
    private final TorrePort torrePort;
    private final CondominioPort condominioPort;

    public ListarTorresPorCondominioUseCaseImpl(TorrePort torrePort, CondominioPort condominioPort) {
        this.torrePort = torrePort;
        this.condominioPort = condominioPort;
    }

    @Override
    public PaginacionResponse<TorreModel> ejecutar(Long condominioId, PaginacionRequest request) {
        if (condominioPort.findById(condominioId) == null) {
            throw new CondominioException("El condominio especificado no existe.");
        }
        return torrePort.findByCondominioId(condominioId, request);
    }
}

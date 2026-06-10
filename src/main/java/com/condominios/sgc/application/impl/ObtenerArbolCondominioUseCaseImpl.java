package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.usecase.ObtenerArbolCondominioUseCase;
import com.condominios.sgc.domain.port.CondominioTreePort;
import com.condominios.sgc.web.dto.CondominioTreeResponse;

public class ObtenerArbolCondominioUseCaseImpl implements ObtenerArbolCondominioUseCase {

    private final CondominioTreePort condominioTreePort;

    public ObtenerArbolCondominioUseCaseImpl(CondominioTreePort condominioTreePort) {
        this.condominioTreePort = condominioTreePort;
    }

    @Override
    public CondominioTreeResponse ejecutar(Long condominioId) {
        return condominioTreePort.obtenerArbolCompleto(condominioId);
    }
}
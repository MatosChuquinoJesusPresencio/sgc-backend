package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.usecase.ObtenerPisoUseCase;
import com.condominios.sgc.domain.exception.PisoException;
import com.condominios.sgc.domain.model.PisoModel;
import com.condominios.sgc.domain.port.PisoPort;

public class ObtenerPisoUseCaseImpl implements ObtenerPisoUseCase {
    private final PisoPort pisoPort;

    public ObtenerPisoUseCaseImpl(PisoPort pisoPort) {
        this.pisoPort = pisoPort;
    }

    @Override
    public PisoModel ejecutar(Long id) {
        return pisoPort.findById(id)
            .orElseThrow(() -> PisoException.noEncontrado(id));
    }
}

package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.usecase.EliminarPisoUseCase;
import com.condominios.sgc.domain.exception.PisoException;
import com.condominios.sgc.domain.port.PisoPort;

public class EliminarPisoUseCaseImpl implements EliminarPisoUseCase {
    private final PisoPort pisoPort;

    public EliminarPisoUseCaseImpl(PisoPort pisoPort) {
        this.pisoPort = pisoPort;
    }

    @Override
    public void ejecutar(Long id) {
        pisoPort.findById(id)
                .orElseThrow(() -> PisoException.noEncontrado(id));
        pisoPort.deleteById(id);
    }
}
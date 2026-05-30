package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.usecase.EliminarTorreUseCase;
import com.condominios.sgc.domain.exception.TorreException;
import com.condominios.sgc.domain.port.TorrePort;

public class EliminarTorreUseCaseImpl implements EliminarTorreUseCase {
    private final TorrePort torrePort;

    public EliminarTorreUseCaseImpl(TorrePort torrePort) {
        this.torrePort = torrePort;
    }

    @Override
    public void ejecutar(Long id) {
        torrePort.findById(id)
                .orElseThrow(() -> TorreException.noEncontrada(id));
        torrePort.deleteById(id);
    }
}

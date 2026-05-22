package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.usecase.ObtenerTorreUseCase;
import com.condominios.sgc.domain.exception.TorreException;
import com.condominios.sgc.domain.model.TorreModel;
import com.condominios.sgc.domain.port.TorrePort;

public class ObtenerTorreUseCaseImpl implements ObtenerTorreUseCase {

    private final TorrePort torrePort;

    public ObtenerTorreUseCaseImpl(TorrePort torrePort) {
        this.torrePort = torrePort;
    }

    @Override
    public TorreModel ejecutar(Long id) {
        TorreModel torre = torrePort.findById(id);
        if (torre == null) {
            throw TorreException.noEncontrada(id);
        }
        return torre;
    }
}

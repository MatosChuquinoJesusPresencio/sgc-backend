package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.dto.CrearPisoRequest;
import com.condominios.sgc.application.usecase.CrearPisoUseCase;
import com.condominios.sgc.domain.exception.TorreException;
import com.condominios.sgc.domain.model.PisoModel;
import com.condominios.sgc.domain.model.TorreModel;
import com.condominios.sgc.domain.port.PisoPort;
import com.condominios.sgc.domain.port.TorrePort;

public class CrearPisoUseCaseImpl implements CrearPisoUseCase {

    private final PisoPort pisoPort;
    private final TorrePort torrePort;

    public CrearPisoUseCaseImpl(PisoPort pisoPort, TorrePort torrePort) {
        this.pisoPort = pisoPort;
        this.torrePort = torrePort;
    }

    @Override
    public PisoModel ejecutar(CrearPisoRequest request) {
        TorreModel torre = torrePort.findById(request.torreId()).orElse(null);
        if (torre == null) {
            throw TorreException.noEncontrada(request.torreId());
        }

        PisoModel piso = new PisoModel(request.numero(), torre.getId());
        return pisoPort.save(piso);
    }
    
}

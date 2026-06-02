package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.dto.ActualizarPisoRequest;
import com.condominios.sgc.application.usecase.ActualizarPisoUseCase;
import com.condominios.sgc.domain.exception.PisoException;
import com.condominios.sgc.domain.model.PisoModel;
import com.condominios.sgc.domain.port.PisoPort;

public class ActualizarPisoUseCaseImpl implements ActualizarPisoUseCase {
    private final PisoPort pisoPort;

    public ActualizarPisoUseCaseImpl(PisoPort pisoPort) {
        this.pisoPort = pisoPort;
    }

    @Override
    public PisoModel ejecutar(Long id, ActualizarPisoRequest request) {
        PisoModel piso = pisoPort.findById(id)
                .orElseThrow(() -> PisoException.noEncontrado(id));

        piso.actualizarDatos(request.numero());

        return pisoPort.save(piso);
    }
}
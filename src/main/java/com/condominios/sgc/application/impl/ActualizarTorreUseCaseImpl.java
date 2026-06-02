package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.dto.ActualizarTorreRequest;
import com.condominios.sgc.application.usecase.ActualizarTorreUseCase;
import com.condominios.sgc.domain.exception.TorreException;
import com.condominios.sgc.domain.model.TorreModel;
import com.condominios.sgc.domain.port.TorrePort;

public class ActualizarTorreUseCaseImpl implements ActualizarTorreUseCase{
    private final TorrePort torrePort;

    public ActualizarTorreUseCaseImpl(TorrePort torrePort) {
        this.torrePort = torrePort;
    }

    @Override
    public TorreModel ejecutar(Long id, ActualizarTorreRequest request) {
        TorreModel torre = torrePort.findById(id)
                .orElseThrow(() -> TorreException.noEncontrada(id));
        torre.actualizarDatos(request.nombre());
        return torrePort.save(torre);
    }

}

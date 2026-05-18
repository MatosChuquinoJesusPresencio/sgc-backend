package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.usecase.ObtenerTorreUseCase;
import com.condominios.sgc.domain.model.TorreModel;
import com.condominios.sgc.domain.port.TorrePort;

public class ActualizarTorreUseCaseImpl {
    private final TorrePort torrePort;
    private final ObtenerTorreUseCase obtenerTorreUseCase;

    public ActualizarTorreUseCaseImpl(TorrePort torrePort, ObtenerTorreUseCase obtenerTorreUseCase) {
        this.torrePort = torrePort;
        this.obtenerTorreUseCase = obtenerTorreUseCase;
    }

    @Override
    public TorreModel ejecutar(Long id, ActualizarTorreRequest request) {
        TorreModel torre = obtenerTorreUseCase.ejecutar(id);
        torre.setNombre(request.nombre());
        return torrePort.save(torre);
    }

}

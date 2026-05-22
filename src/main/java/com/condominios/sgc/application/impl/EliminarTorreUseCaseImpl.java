package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.usecase.EliminarTorreUseCase;
import com.condominios.sgc.application.usecase.ObtenerTorreUseCase;
import com.condominios.sgc.domain.port.TorrePort;

public class EliminarTorreUseCaseImpl implements EliminarTorreUseCase {
    private final TorrePort torrePort;
    private final ObtenerTorreUseCase obtenerTorreUseCase;

    public EliminarTorreUseCaseImpl(TorrePort torrePort, ObtenerTorreUseCase obtenerTorreUseCase) {
        this.torrePort = torrePort;
        this.obtenerTorreUseCase = obtenerTorreUseCase;
    }

    @Override
    public void ejecutar(Long id) {
        obtenerTorreUseCase.ejecutar(id);
        torrePort.deleteById(id);
    }
}

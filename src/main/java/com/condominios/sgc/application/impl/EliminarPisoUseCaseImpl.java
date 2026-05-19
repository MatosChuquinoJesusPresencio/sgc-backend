package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.usecase.EliminarPisoUseCase;
import com.condominios.sgc.application.usecase.ObtenerPisoUseCase;
import com.condominios.sgc.domain.port.PisoPort;

public class EliminarPisoUseCaseImpl implements EliminarPisoUseCase {
    private final PisoPort pisoPort;
    private final ObtenerPisoUseCase obtenerPisoUseCase;

    public EliminarPisoUseCaseImpl(PisoPort pisoPort, ObtenerPisoUseCase obtenerPisoUseCase) {
        this.pisoPort = pisoPort;
        this.obtenerPisoUseCase = obtenerPisoUseCase;
    }

    @Override
    public void ejecutar(Long id) {
        obtenerPisoUseCase.ejecutar(id);
        pisoPort.deleteById(id);
    }
}
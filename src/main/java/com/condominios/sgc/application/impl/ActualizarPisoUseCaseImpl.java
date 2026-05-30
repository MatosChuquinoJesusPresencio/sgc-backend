package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.dto.ActualizarPisoRequest;
import com.condominios.sgc.application.usecase.ActualizarPisoUseCase;
import com.condominios.sgc.application.usecase.ObtenerPisoUseCase;
import com.condominios.sgc.domain.model.PisoModel;
import com.condominios.sgc.domain.port.PisoPort;

public class ActualizarPisoUseCaseImpl implements ActualizarPisoUseCase {
    private final PisoPort pisoPort;
    private final ObtenerPisoUseCase obtenerPisoUseCase;

    public ActualizarPisoUseCaseImpl(PisoPort pisoPort, ObtenerPisoUseCase obtenerPisoUseCase) {
        this.pisoPort = pisoPort;
        this.obtenerPisoUseCase = obtenerPisoUseCase;
    }

    @Override
    public PisoModel ejecutar(Long id, ActualizarPisoRequest request) {
        PisoModel piso = obtenerPisoUseCase.ejecutar(id);

        piso.actualizar(request.numero(), piso.getTorreId());

        return pisoPort.save(piso);
    }
}
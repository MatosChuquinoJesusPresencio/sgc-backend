package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.usecase.ObtenerEstacionamientoUseCase;
import com.condominios.sgc.domain.exception.EstacionamientoException;
import com.condominios.sgc.domain.model.EstacionamientoModel;
import com.condominios.sgc.domain.port.EstacionamientoPort;

public class ObtenerEstacionamientoUseCaseImpl implements ObtenerEstacionamientoUseCase {

    private final EstacionamientoPort estacionamientoPort;

    public ObtenerEstacionamientoUseCaseImpl(EstacionamientoPort estacionamientoPort) {
        this.estacionamientoPort = estacionamientoPort;
    }

    @Override
    public EstacionamientoModel ejecutar(Long id) {
        return estacionamientoPort.findById(id)
                .orElseThrow(() -> EstacionamientoException.noEncontrado(id));
    }
}

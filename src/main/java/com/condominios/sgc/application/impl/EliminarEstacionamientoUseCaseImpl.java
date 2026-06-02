package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.usecase.EliminarEstacionamientoUseCase;
import com.condominios.sgc.domain.exception.EstacionamientoException;
import com.condominios.sgc.domain.port.EstacionamientoPort;

public class EliminarEstacionamientoUseCaseImpl implements EliminarEstacionamientoUseCase {

    private final EstacionamientoPort estacionamientoPort;

    public EliminarEstacionamientoUseCaseImpl(EstacionamientoPort estacionamientoPort) {
        this.estacionamientoPort = estacionamientoPort;
    }

    @Override
    public void ejecutar(Long id) {
        estacionamientoPort.findById(id)
                .orElseThrow(() -> EstacionamientoException.noEncontrado(id));

        estacionamientoPort.deleteById(id);
    }
}

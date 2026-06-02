package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.dto.ActualizarEstacionamientoRequest;
import com.condominios.sgc.application.usecase.ActualizarEstacionamientoUseCase;
import com.condominios.sgc.domain.exception.EstacionamientoException;
import com.condominios.sgc.domain.model.EstacionamientoModel;
import com.condominios.sgc.domain.port.EstacionamientoPort;

public class ActualizarEstacionamientoUseCaseImpl implements ActualizarEstacionamientoUseCase {

    private final EstacionamientoPort estacionamientoPort;

    public ActualizarEstacionamientoUseCaseImpl(EstacionamientoPort estacionamientoPort) {
        this.estacionamientoPort = estacionamientoPort;
    }

    @Override
    public EstacionamientoModel ejecutar(Long id, ActualizarEstacionamientoRequest request) {
        EstacionamientoModel estacionamiento = estacionamientoPort.findById(id)
                .orElseThrow(() -> EstacionamientoException.noEncontrado(id));

        estacionamiento.actualizarDatos(request.numero());

        return estacionamientoPort.save(estacionamiento);
    }
}

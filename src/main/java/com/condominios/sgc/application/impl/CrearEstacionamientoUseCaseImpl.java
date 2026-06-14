package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.dto.command.CrearEstacionamientoCommand;
import com.condominios.sgc.application.dto.response.EstacionamientoResponse;
import com.condominios.sgc.application.usecase.CrearEstacionamientoUseCase;
import com.condominios.sgc.domain.model.EstacionamientoModel;
import com.condominios.sgc.domain.port.EstacionamientoPort;

public class CrearEstacionamientoUseCaseImpl implements CrearEstacionamientoUseCase {
    private final EstacionamientoPort estacionamientoPort;

    public CrearEstacionamientoUseCaseImpl(EstacionamientoPort estacionamientoPort) {
        this.estacionamientoPort = estacionamientoPort;
    }

    @Override
    public EstacionamientoResponse ejecutar(CrearEstacionamientoCommand command) {
        EstacionamientoModel estacionamiento = new EstacionamientoModel(
            command.numero(), command.idCondominio());

        estacionamiento = estacionamientoPort.guardar(estacionamiento);

        return EstacionamientoResponse.desdeModelo(estacionamiento);
    }
}

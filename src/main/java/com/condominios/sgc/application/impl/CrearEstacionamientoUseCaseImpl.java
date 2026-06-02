package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.dto.CrearEstacionamientoRequest;
import com.condominios.sgc.application.usecase.CrearEstacionamientoUseCase;
import com.condominios.sgc.domain.model.EstacionamientoModel;
import com.condominios.sgc.domain.port.EstacionamientoPort;

public class CrearEstacionamientoUseCaseImpl implements CrearEstacionamientoUseCase {

    private final EstacionamientoPort estacionamientoPort;

    public CrearEstacionamientoUseCaseImpl(EstacionamientoPort estacionamientoPort) {
        this.estacionamientoPort = estacionamientoPort;
    }

    @Override
    public EstacionamientoModel ejecutar(CrearEstacionamientoRequest request) {
        EstacionamientoModel modelo = new EstacionamientoModel(
                request.numero(),
                request.capacidadMaxima(),
                request.cantidadActual(),
                request.disponible(),
                request.condominioId());

        return estacionamientoPort.save(modelo);
    }
}

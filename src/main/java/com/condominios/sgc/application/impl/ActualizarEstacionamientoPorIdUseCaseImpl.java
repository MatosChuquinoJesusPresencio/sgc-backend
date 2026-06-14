package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.dto.command.ActualizarEstacionamientoCommand;
import com.condominios.sgc.application.dto.response.EstacionamientoResponse;
import com.condominios.sgc.application.usecase.ActualizarEstacionamientoPorIdUseCase;
import com.condominios.sgc.domain.exception.EstacionamientoException;
import com.condominios.sgc.domain.model.EstacionamientoModel;
import com.condominios.sgc.domain.port.EstacionamientoPort;

public class ActualizarEstacionamientoPorIdUseCaseImpl implements ActualizarEstacionamientoPorIdUseCase {
    private final EstacionamientoPort estacionamientoPort;

    public ActualizarEstacionamientoPorIdUseCaseImpl(EstacionamientoPort estacionamientoPort) {
        this.estacionamientoPort = estacionamientoPort;
    }

    @Override
    public EstacionamientoResponse ejecutar(Long id, ActualizarEstacionamientoCommand command) {
        EstacionamientoModel estacionamiento = estacionamientoPort.obtenerPorId(id)
            .orElseThrow(EstacionamientoException::noEncontrado);

        estacionamiento.actualizarNumero(command.numero());

        if (command.tipoVehiculo() != null) {
            estacionamiento.configurar(command.tipoVehiculo(), command.capacidadMaxima());
        } else {
            estacionamiento.reiniciar();
        }

        if (command.desasignarApartamento()) {
            estacionamiento.desasignarApartamento();
        } else if (command.idApartamento() != null) {
            estacionamiento.asignarApartamento(command.idApartamento());
        }

        estacionamiento = estacionamientoPort.guardar(estacionamiento);

        return EstacionamientoResponse.desdeModelo(estacionamiento);
    }
}

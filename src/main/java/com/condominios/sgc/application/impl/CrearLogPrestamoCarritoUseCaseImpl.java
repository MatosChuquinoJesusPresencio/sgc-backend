package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.dto.command.CrearLogPrestamoCarritoCommand;
import com.condominios.sgc.application.dto.response.LogPrestamoCarritoResponse;
import com.condominios.sgc.application.usecase.CrearLogPrestamoCarritoUseCase;
import com.condominios.sgc.domain.auxiliar.TipoHabitante;
import com.condominios.sgc.domain.model.LogPrestamoCarritoModel;
import com.condominios.sgc.domain.port.LogPrestamoCarritoPort;

public class CrearLogPrestamoCarritoUseCaseImpl implements CrearLogPrestamoCarritoUseCase {
    private final LogPrestamoCarritoPort logPrestamoCarritoPort;

    public CrearLogPrestamoCarritoUseCaseImpl(LogPrestamoCarritoPort logPrestamoCarritoPort) {
        this.logPrestamoCarritoPort = logPrestamoCarritoPort;
    }

    @Override
    public LogPrestamoCarritoResponse ejecutar(CrearLogPrestamoCarritoCommand command) {
        TipoHabitante solicitante = command.idPropietario() != null
            ? TipoHabitante.PROPIETARIO : TipoHabitante.INQUILINO;

        LogPrestamoCarritoModel log = new LogPrestamoCarritoModel(
            solicitante, command.nombreSolicitante(), command.dniSolicitante(),
            command.idApartamento(), command.idCarrito());

        if (command.idPropietario() != null) {
            log.asignarPropietario(command.idPropietario());
        } else {
            log.asignarInquilino(command.idInquilino());
        }

        log = logPrestamoCarritoPort.guardar(log);

        return LogPrestamoCarritoResponse.desdeModelo(log);
    }
}

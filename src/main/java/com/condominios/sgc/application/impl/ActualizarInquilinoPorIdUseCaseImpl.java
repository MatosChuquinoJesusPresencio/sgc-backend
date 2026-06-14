package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.dto.command.ActualizarInquilinoCommand;
import com.condominios.sgc.application.dto.response.InquilinoResponse;
import com.condominios.sgc.application.usecase.ActualizarInquilinoPorIdUseCase;
import com.condominios.sgc.domain.exception.InquilinoException;
import com.condominios.sgc.domain.model.InquilinoModel;
import com.condominios.sgc.domain.port.InquilinoPort;

public class ActualizarInquilinoPorIdUseCaseImpl implements ActualizarInquilinoPorIdUseCase {
    private final InquilinoPort inquilinoPort;

    public ActualizarInquilinoPorIdUseCaseImpl(InquilinoPort inquilinoPort) {
        this.inquilinoPort = inquilinoPort;
    }

    @Override
    public InquilinoResponse ejecutar(Long id, ActualizarInquilinoCommand command) {
        InquilinoModel inquilino = inquilinoPort.obtenerPorId(id)
            .orElseThrow(InquilinoException::noEncontrado);

        inquilino.actualizar(command.nombres(), command.apellidos(),
            command.tipoDocumento(), command.numeroDocumento());

        if (command.desasignarApartamento()) {
            inquilino.desasignarApartamento();
        } else if (command.idApartamento() != null) {
            inquilino.asignarApartamento(command.idApartamento());
        }

        inquilino = inquilinoPort.guardar(inquilino);

        return InquilinoResponse.desdeModelo(inquilino);
    }
}

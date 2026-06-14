package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.dto.command.CrearInquilinoCommand;
import com.condominios.sgc.application.dto.response.InquilinoResponse;
import com.condominios.sgc.application.usecase.CrearInquilinoUseCase;
import com.condominios.sgc.domain.model.InquilinoModel;
import com.condominios.sgc.domain.port.InquilinoPort;

public class CrearInquilinoUseCaseImpl implements CrearInquilinoUseCase {
    private final InquilinoPort inquilinoPort;

    public CrearInquilinoUseCaseImpl(InquilinoPort inquilinoPort) {
        this.inquilinoPort = inquilinoPort;
    }

    @Override
    public InquilinoResponse ejecutar(CrearInquilinoCommand command) {
        InquilinoModel inquilino = new InquilinoModel(
            command.nombres(), command.apellidos(), command.tipoDocumento(),
            command.numeroDocumento(), command.idApartamento());

        inquilino = inquilinoPort.guardar(inquilino);

        return InquilinoResponse.desdeModelo(inquilino);
    }
}

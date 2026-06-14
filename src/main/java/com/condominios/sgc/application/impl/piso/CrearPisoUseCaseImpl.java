package com.condominios.sgc.application.impl.piso;

import com.condominios.sgc.application.dto.command.CrearPisoCommand;
import com.condominios.sgc.application.dto.response.PisoResponse;
import com.condominios.sgc.application.usecase.piso.CrearPisoUseCase;
import com.condominios.sgc.domain.model.PisoModel;
import com.condominios.sgc.domain.port.PisoPort;

public class CrearPisoUseCaseImpl implements CrearPisoUseCase {
    private final PisoPort pisoPort;

    public CrearPisoUseCaseImpl(PisoPort pisoPort) {
        this.pisoPort = pisoPort;
    }

    @Override
    public PisoResponse ejecutar(CrearPisoCommand command) {
        PisoModel piso = new PisoModel(command.numero(), command.idTorre());
        piso = pisoPort.guardar(piso);
        return PisoResponse.desdeModelo(piso);
    }
}

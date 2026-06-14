package com.condominios.sgc.application.impl.torre;

import com.condominios.sgc.application.dto.command.CrearTorreCommand;
import com.condominios.sgc.application.dto.response.TorreResponse;
import com.condominios.sgc.application.usecase.torre.CrearTorreUseCase;
import com.condominios.sgc.domain.model.TorreModel;
import com.condominios.sgc.domain.port.TorrePort;

public class CrearTorreUseCaseImpl implements CrearTorreUseCase {
    private final TorrePort torrePort;

    public CrearTorreUseCaseImpl(TorrePort torrePort) {
        this.torrePort = torrePort;
    }

    @Override
    public TorreResponse ejecutar(CrearTorreCommand command) {
        TorreModel torre = new TorreModel(command.nombre(), command.idCondominio());
        torre = torrePort.guardar(torre);
        return TorreResponse.desdeModelo(torre);
    }
}

package com.condominios.sgc.application.usecase.piso;

import com.condominios.sgc.application.dto.command.CrearPisoCommand;
import com.condominios.sgc.application.dto.response.PisoResponse;

public interface CrearPisoUseCase {
    PisoResponse ejecutar(CrearPisoCommand command);
}

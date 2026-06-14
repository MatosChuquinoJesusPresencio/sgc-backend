package com.condominios.sgc.application.usecase.torre;

import com.condominios.sgc.application.dto.command.CrearTorreCommand;
import com.condominios.sgc.application.dto.response.TorreResponse;

public interface CrearTorreUseCase {
    TorreResponse ejecutar(CrearTorreCommand command);
}

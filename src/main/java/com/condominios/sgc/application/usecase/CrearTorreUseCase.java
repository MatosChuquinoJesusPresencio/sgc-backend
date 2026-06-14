package com.condominios.sgc.application.usecase;

import com.condominios.sgc.application.dto.command.CrearTorreCommand;
import com.condominios.sgc.application.dto.response.TorreResponse;

public interface CrearTorreUseCase {
    TorreResponse ejecutar(CrearTorreCommand command);
}

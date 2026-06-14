package com.condominios.sgc.application.usecase;

import com.condominios.sgc.application.dto.command.CrearEstacionamientoCommand;
import com.condominios.sgc.application.dto.response.EstacionamientoResponse;

public interface CrearEstacionamientoUseCase {
    EstacionamientoResponse ejecutar(CrearEstacionamientoCommand command);
}

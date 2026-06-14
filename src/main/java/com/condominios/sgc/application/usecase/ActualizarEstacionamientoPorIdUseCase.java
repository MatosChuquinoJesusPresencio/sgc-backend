package com.condominios.sgc.application.usecase;

import com.condominios.sgc.application.dto.command.ActualizarEstacionamientoCommand;
import com.condominios.sgc.application.dto.response.EstacionamientoResponse;

public interface ActualizarEstacionamientoPorIdUseCase {
    EstacionamientoResponse ejecutar(Long id, ActualizarEstacionamientoCommand command);
}

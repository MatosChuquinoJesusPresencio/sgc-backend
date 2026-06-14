package com.condominios.sgc.application.usecase;

import com.condominios.sgc.application.dto.command.ActualizarVehiculoCommand;
import com.condominios.sgc.application.dto.response.VehiculoResponse;

public interface ActualizarVehiculoPorIdUseCase {
    VehiculoResponse ejecutar(Long id, ActualizarVehiculoCommand command);
}

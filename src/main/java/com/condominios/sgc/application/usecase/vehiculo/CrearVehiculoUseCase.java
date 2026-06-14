package com.condominios.sgc.application.usecase.vehiculo;

import com.condominios.sgc.application.dto.command.CrearVehiculoCommand;
import com.condominios.sgc.application.dto.response.VehiculoResponse;

public interface CrearVehiculoUseCase {
    VehiculoResponse ejecutar(CrearVehiculoCommand command);
}

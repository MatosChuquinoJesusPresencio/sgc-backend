package com.condominios.sgc.application.usecase;

import com.condominios.sgc.application.dto.command.CrearCarritoCommand;
import com.condominios.sgc.application.dto.response.CarritoResponse;

public interface CrearCarritoUseCase {
    CarritoResponse ejecutar(CrearCarritoCommand command);
}

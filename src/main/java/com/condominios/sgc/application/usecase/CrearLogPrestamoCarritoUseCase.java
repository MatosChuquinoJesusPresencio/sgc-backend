package com.condominios.sgc.application.usecase;

import com.condominios.sgc.application.dto.command.CrearLogPrestamoCarritoCommand;
import com.condominios.sgc.application.dto.response.LogPrestamoCarritoResponse;

public interface CrearLogPrestamoCarritoUseCase {
    LogPrestamoCarritoResponse ejecutar(CrearLogPrestamoCarritoCommand command);
}

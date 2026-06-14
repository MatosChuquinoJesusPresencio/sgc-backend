package com.condominios.sgc.application.usecase;

import com.condominios.sgc.application.dto.command.CrearTokenCommand;
import com.condominios.sgc.application.dto.response.TokenResponse;

public interface CrearTokenUseCase {
    TokenResponse ejecutar(CrearTokenCommand command);
}

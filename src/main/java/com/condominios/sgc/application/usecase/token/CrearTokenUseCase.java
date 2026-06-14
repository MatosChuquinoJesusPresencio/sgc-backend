package com.condominios.sgc.application.usecase.token;

import com.condominios.sgc.application.dto.command.CrearTokenCommand;
import com.condominios.sgc.application.dto.response.TokenResponse;

public interface CrearTokenUseCase {
    TokenResponse ejecutar(CrearTokenCommand command);
}

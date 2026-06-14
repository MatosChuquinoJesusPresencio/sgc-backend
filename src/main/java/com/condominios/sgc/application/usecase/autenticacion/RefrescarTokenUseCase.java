package com.condominios.sgc.application.usecase.autenticacion;

import com.condominios.sgc.application.dto.command.RefrescarTokenCommand;
import com.condominios.sgc.application.dto.response.LoginResponse;

public interface RefrescarTokenUseCase {
    LoginResponse ejecutar(RefrescarTokenCommand command);
}

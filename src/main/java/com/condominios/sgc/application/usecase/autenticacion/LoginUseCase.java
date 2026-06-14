package com.condominios.sgc.application.usecase.autenticacion;

import com.condominios.sgc.application.dto.command.LoginCommand;
import com.condominios.sgc.application.dto.response.LoginResponse;

public interface LoginUseCase {
    LoginResponse ejecutar(LoginCommand command);
}

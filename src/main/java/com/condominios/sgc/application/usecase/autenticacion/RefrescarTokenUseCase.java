package com.condominios.sgc.application.usecase.autenticacion;

import com.condominios.sgc.application.dto.command.RefrescarTokenCommand;
import com.condominios.sgc.application.dto.response.IniciarSesionResponse;

public interface RefrescarTokenUseCase {
    IniciarSesionResponse ejecutar(RefrescarTokenCommand command);
}

package com.condominios.sgc.application.usecase.autenticacion;

import com.condominios.sgc.application.dto.command.IniciarSesionCommand;
import com.condominios.sgc.application.dto.response.IniciarSesionResponse;

public interface IniciarSesionUseCase {
    IniciarSesionResponse ejecutar(IniciarSesionCommand command);
}

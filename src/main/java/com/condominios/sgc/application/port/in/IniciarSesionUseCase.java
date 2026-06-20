package com.condominios.sgc.application.port.in;

import com.condominios.sgc.application.dto.command.IniciarSesionCommand;
import com.condominios.sgc.application.dto.result.SesionUsuarioResult;

public interface IniciarSesionUseCase {
    SesionUsuarioResult ejecutar(IniciarSesionCommand command);
}

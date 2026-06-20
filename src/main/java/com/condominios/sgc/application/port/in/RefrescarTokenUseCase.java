package com.condominios.sgc.application.port.in;

import com.condominios.sgc.application.dto.command.RefrescarTokenCommand;
import com.condominios.sgc.application.dto.result.SesionUsuarioResult;

public interface RefrescarTokenUseCase {
    SesionUsuarioResult ejecutar(RefrescarTokenCommand command);
}

package com.condominios.sgc.application.port.in;

import com.condominios.sgc.application.dto.command.CerrarSesionCommand;

public interface CerrarSesionUseCase {
    void ejecutar(CerrarSesionCommand command);
}

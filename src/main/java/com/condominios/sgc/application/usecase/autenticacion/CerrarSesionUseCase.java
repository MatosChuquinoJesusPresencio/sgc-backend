package com.condominios.sgc.application.usecase.autenticacion;

import com.condominios.sgc.application.dto.command.CerrarSesionCommand;

public interface CerrarSesionUseCase {
    void ejecutar(CerrarSesionCommand command);
}

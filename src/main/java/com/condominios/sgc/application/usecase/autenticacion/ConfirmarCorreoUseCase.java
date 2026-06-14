package com.condominios.sgc.application.usecase.autenticacion;

import com.condominios.sgc.application.dto.command.ConfirmarCorreoCommand;

public interface ConfirmarCorreoUseCase {
    void ejecutar(ConfirmarCorreoCommand command);
}

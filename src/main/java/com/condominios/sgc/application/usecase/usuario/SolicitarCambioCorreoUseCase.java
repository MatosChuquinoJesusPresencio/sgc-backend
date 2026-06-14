package com.condominios.sgc.application.usecase.usuario;

import com.condominios.sgc.application.dto.command.SolicitarCambioCorreoCommand;

public interface SolicitarCambioCorreoUseCase {
    void ejecutar(SolicitarCambioCorreoCommand command);
}

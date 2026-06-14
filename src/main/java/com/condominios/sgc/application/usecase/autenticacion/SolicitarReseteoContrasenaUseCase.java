package com.condominios.sgc.application.usecase.autenticacion;

import com.condominios.sgc.application.dto.command.SolicitarReseteoContrasenaCommand;

public interface SolicitarReseteoContrasenaUseCase {
    void ejecutar(SolicitarReseteoContrasenaCommand command);
}

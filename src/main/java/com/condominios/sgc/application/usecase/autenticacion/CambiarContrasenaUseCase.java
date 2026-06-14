package com.condominios.sgc.application.usecase.autenticacion;

import com.condominios.sgc.application.dto.command.CambiarContrasenaCommand;

public interface CambiarContrasenaUseCase {
    void ejecutar(CambiarContrasenaCommand command);
}

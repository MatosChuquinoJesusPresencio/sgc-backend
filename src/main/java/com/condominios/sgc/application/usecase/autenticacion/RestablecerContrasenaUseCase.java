package com.condominios.sgc.application.usecase.autenticacion;

import com.condominios.sgc.application.dto.command.RestablecerContrasenaCommand;

public interface RestablecerContrasenaUseCase {
    void ejecutar(RestablecerContrasenaCommand command);
}

package com.condominios.sgc.application.port.in;

import com.condominios.sgc.application.dto.command.RestablecerContrasenaCommand;

public interface RestablecerContrasenaUseCase {
    void ejecutar(RestablecerContrasenaCommand command);
}

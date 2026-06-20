package com.condominios.sgc.application.port.in;

import com.condominios.sgc.application.dto.command.OlvidasteContrasenaCommand;

public interface OlvidasteContrasenaUseCase {
    void ejecutar(OlvidasteContrasenaCommand command);
}

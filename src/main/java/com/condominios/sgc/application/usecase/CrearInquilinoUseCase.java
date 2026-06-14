package com.condominios.sgc.application.usecase;

import com.condominios.sgc.application.dto.command.CrearInquilinoCommand;
import com.condominios.sgc.application.dto.response.InquilinoResponse;

public interface CrearInquilinoUseCase {
    InquilinoResponse ejecutar(CrearInquilinoCommand command);
}

package com.condominios.sgc.application.usecase;

import com.condominios.sgc.application.dto.command.ActualizarInquilinoCommand;
import com.condominios.sgc.application.dto.response.InquilinoResponse;

public interface ActualizarInquilinoPorIdUseCase {
    InquilinoResponse ejecutar(Long id, ActualizarInquilinoCommand command);
}

package com.condominios.sgc.application.usecase;

import com.condominios.sgc.application.dto.ActualizarInquilinoRequest;
import com.condominios.sgc.application.dto.InquilinoResponse;

public interface ActualizarInquilinoUseCase {
    InquilinoResponse ejecutar(Long id, ActualizarInquilinoRequest request);
}

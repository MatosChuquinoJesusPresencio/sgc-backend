package com.condominios.sgc.application.usecase;

import com.condominios.sgc.application.dto.ActualizarInquilinoRequest;
import com.condominios.sgc.domain.model.InquilinoModel;

public interface ActualizarInquilinoUseCase {
    InquilinoModel ejecutar(Long id, ActualizarInquilinoRequest request);
}

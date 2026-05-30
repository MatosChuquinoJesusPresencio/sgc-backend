package com.condominios.sgc.application.usecase;

import com.condominios.sgc.application.dto.CrearInquilinoRequest;
import com.condominios.sgc.domain.model.InquilinoModel;

public interface CrearInquilinoUseCase {
    InquilinoModel ejecutar(CrearInquilinoRequest request);
}

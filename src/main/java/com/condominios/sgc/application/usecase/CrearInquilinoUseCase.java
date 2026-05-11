package com.condominios.sgc.application.usecase;

import com.condominios.sgc.application.dto.CrearInquilinoRequest;
import com.condominios.sgc.application.dto.InquilinoResponse;

public interface CrearInquilinoUseCase {
    InquilinoResponse crear(CrearInquilinoRequest request);
}

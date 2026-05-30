package com.condominios.sgc.application.usecase;

import com.condominios.sgc.domain.model.InquilinoModel;

public interface ObtenerInquilinoUseCase {
    InquilinoModel ejecutar(Long id);
}

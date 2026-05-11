package com.condominios.sgc.application.usecase;

import com.condominios.sgc.application.dto.InquilinoResponse;

public interface ObtenerInquilinoUseCase {
    InquilinoResponse obtenerPorId(Long id);
}

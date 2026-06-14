package com.condominios.sgc.application.usecase.inquilino;

import com.condominios.sgc.application.dto.response.InquilinoResponse;

public interface ObtenerInquilinoPorIdUseCase {
    InquilinoResponse ejecutar(Long id);
}

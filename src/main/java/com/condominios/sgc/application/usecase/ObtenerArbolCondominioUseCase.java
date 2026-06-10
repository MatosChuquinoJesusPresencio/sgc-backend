package com.condominios.sgc.application.usecase;

import com.condominios.sgc.web.dto.CondominioTreeResponse;

public interface ObtenerArbolCondominioUseCase {
    CondominioTreeResponse ejecutar(Long condominioId);
}
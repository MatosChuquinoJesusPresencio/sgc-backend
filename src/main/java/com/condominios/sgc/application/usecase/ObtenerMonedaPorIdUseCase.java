package com.condominios.sgc.application.usecase;

import com.condominios.sgc.application.dto.response.MonedaResponse;

public interface ObtenerMonedaPorIdUseCase {
    MonedaResponse ejecutar(Long id);
}

package com.condominios.sgc.application.usecase;

import com.condominios.sgc.application.dto.response.TorreResponse;

public interface ObtenerTorrePorIdUseCase {
    TorreResponse ejecutar(Long id);
}

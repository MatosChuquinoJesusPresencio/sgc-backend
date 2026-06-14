package com.condominios.sgc.application.usecase.torre;

import com.condominios.sgc.application.dto.response.TorreResponse;

public interface ObtenerTorrePorIdUseCase {
    TorreResponse ejecutar(Long id);
}

package com.condominios.sgc.application.usecase.torre;

import com.condominios.sgc.application.dto.response.TorreResponse;

public interface ActualizarTorrePorIdUseCase {
    TorreResponse ejecutar(Long id, String nombre);
}

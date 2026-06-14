package com.condominios.sgc.application.usecase;

import com.condominios.sgc.application.dto.response.TorreResponse;

public interface ActualizarTorrePorIdUseCase {
    TorreResponse ejecutar(Long id, String nombre);
}

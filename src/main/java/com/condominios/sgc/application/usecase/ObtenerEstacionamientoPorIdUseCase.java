package com.condominios.sgc.application.usecase;

import com.condominios.sgc.application.dto.response.EstacionamientoResponse;

public interface ObtenerEstacionamientoPorIdUseCase {
    EstacionamientoResponse ejecutar(Long id);
}

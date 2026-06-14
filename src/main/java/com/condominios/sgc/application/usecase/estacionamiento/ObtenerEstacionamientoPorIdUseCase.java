package com.condominios.sgc.application.usecase.estacionamiento;

import com.condominios.sgc.application.dto.response.EstacionamientoResponse;

public interface ObtenerEstacionamientoPorIdUseCase {
    EstacionamientoResponse ejecutar(Long id);
}

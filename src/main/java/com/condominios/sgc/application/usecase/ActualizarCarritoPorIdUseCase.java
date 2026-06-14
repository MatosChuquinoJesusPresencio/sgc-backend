package com.condominios.sgc.application.usecase;

import com.condominios.sgc.application.dto.response.CarritoResponse;

public interface ActualizarCarritoPorIdUseCase {
    CarritoResponse ejecutar(Long id, String codigo);
}

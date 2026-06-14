package com.condominios.sgc.application.usecase;

import com.condominios.sgc.application.dto.response.LogPrestamoCarritoResponse;

public interface ObtenerLogPrestamoCarritoPorIdUseCase {
    LogPrestamoCarritoResponse ejecutar(Long id);
}

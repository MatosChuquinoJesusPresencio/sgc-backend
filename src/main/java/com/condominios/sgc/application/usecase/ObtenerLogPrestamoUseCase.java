package com.condominios.sgc.application.usecase;

import com.condominios.sgc.domain.model.LogPrestamoCarritoModel;

public interface ObtenerLogPrestamoUseCase {
    LogPrestamoCarritoModel ejecutar(Long id);
}

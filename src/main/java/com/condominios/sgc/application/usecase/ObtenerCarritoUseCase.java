package com.condominios.sgc.application.usecase;

import com.condominios.sgc.domain.model.CarritoModel;

public interface ObtenerCarritoUseCase {
    CarritoModel ejecutar(Long id);
}

package com.condominios.sgc.application.usecase;

import com.condominios.sgc.application.dto.ActualizarCarritoRequest;
import com.condominios.sgc.domain.model.CarritoModel;

public interface ActualizarCarritoUseCase {
    CarritoModel ejecutar(Long id, ActualizarCarritoRequest request);
}

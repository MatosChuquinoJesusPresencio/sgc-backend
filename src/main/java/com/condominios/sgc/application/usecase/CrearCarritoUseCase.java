package com.condominios.sgc.application.usecase;

import com.condominios.sgc.application.dto.CrearCarritoRequest;
import com.condominios.sgc.domain.model.CarritoModel;

public interface CrearCarritoUseCase {
    CarritoModel ejecutar(CrearCarritoRequest request);
}

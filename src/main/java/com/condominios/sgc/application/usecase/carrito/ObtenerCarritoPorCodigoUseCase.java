package com.condominios.sgc.application.usecase.carrito;

import com.condominios.sgc.application.dto.response.CarritoResponse;

public interface ObtenerCarritoPorCodigoUseCase {
    CarritoResponse ejecutar(String codigo);
}

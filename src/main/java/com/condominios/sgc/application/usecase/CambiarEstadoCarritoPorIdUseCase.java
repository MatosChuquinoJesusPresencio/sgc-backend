package com.condominios.sgc.application.usecase;

import com.condominios.sgc.domain.auxiliar.EstadoCarrito;
import com.condominios.sgc.application.dto.response.CarritoResponse;

public interface CambiarEstadoCarritoPorIdUseCase {
    CarritoResponse ejecutar(Long id, EstadoCarrito estado);
}

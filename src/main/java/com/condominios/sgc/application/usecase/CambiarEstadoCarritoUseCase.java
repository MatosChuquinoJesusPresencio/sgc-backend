package com.condominios.sgc.application.usecase;

import com.condominios.sgc.domain.auxiliar.EstadoCarrito;
import com.condominios.sgc.domain.model.CarritoModel;

public interface CambiarEstadoCarritoUseCase {
    CarritoModel ejecutar(Long id, EstadoCarrito nuevoEstado);
}

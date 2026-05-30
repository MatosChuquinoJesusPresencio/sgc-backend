package com.condominios.sgc.application.usecase;

import com.condominios.sgc.application.dto.ActualizarVehiculoRequest;
import com.condominios.sgc.domain.model.VehiculoModel;

public interface ActualizarVehiculoUseCase {
    VehiculoModel ejecutar(Long id, ActualizarVehiculoRequest request);
}

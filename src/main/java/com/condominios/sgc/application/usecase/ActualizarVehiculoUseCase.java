package com.condominios.sgc.application.usecase;

import com.condominios.sgc.application.dto.ActualizarVehiculoRequest;
import com.condominios.sgc.application.dto.VehiculoResponse;

public interface ActualizarVehiculoUseCase {
    VehiculoResponse ejecutar(Long id, ActualizarVehiculoRequest request);
}

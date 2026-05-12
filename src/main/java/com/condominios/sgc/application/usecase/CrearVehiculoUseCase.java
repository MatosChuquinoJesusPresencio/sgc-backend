package com.condominios.sgc.application.usecase;

import com.condominios.sgc.application.dto.CrearVehiculoRequest;
import com.condominios.sgc.application.dto.VehiculoResponse;

public interface CrearVehiculoUseCase {
    VehiculoResponse crear(CrearVehiculoRequest request);
}

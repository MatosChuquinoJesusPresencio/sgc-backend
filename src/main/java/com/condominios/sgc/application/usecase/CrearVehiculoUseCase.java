package com.condominios.sgc.application.usecase;

import com.condominios.sgc.application.dto.CrearVehiculoRequest;
import com.condominios.sgc.domain.model.VehiculoModel;

public interface CrearVehiculoUseCase {
    VehiculoModel ejecutar(CrearVehiculoRequest request);
}

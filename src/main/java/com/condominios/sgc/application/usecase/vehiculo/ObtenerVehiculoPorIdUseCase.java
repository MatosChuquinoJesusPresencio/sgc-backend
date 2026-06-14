package com.condominios.sgc.application.usecase.vehiculo;

import com.condominios.sgc.application.dto.response.VehiculoResponse;

public interface ObtenerVehiculoPorIdUseCase {
    VehiculoResponse ejecutar(Long id);
}

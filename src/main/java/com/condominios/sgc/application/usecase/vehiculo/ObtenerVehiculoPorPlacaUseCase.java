package com.condominios.sgc.application.usecase.vehiculo;

import com.condominios.sgc.application.dto.response.VehiculoResponse;

public interface ObtenerVehiculoPorPlacaUseCase {
    VehiculoResponse ejecutar(String placa);
}

package com.condominios.sgc.application.usecase;

import com.condominios.sgc.application.dto.VehiculoResponse;

public interface ObtenerVehiculoUseCase {
    VehiculoResponse obtenerPorId(Long id);
}

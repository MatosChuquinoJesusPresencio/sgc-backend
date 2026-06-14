package com.condominios.sgc.application.usecase;

import com.condominios.sgc.application.dto.response.CiudadResponse;

public interface ObtenerCiudadPorIdUseCase {
    CiudadResponse ejecutar(Long id);
}

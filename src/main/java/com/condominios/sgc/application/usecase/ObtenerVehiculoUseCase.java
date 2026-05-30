package com.condominios.sgc.application.usecase;

import com.condominios.sgc.domain.model.VehiculoModel;

public interface ObtenerVehiculoUseCase {
    VehiculoModel ejecutar(Long id);
}

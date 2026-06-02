package com.condominios.sgc.application.usecase;

import com.condominios.sgc.domain.model.LogAccesoVehicularModel;

public interface ObtenerLogAccesoUseCase {
    LogAccesoVehicularModel ejecutar(Long id);
}

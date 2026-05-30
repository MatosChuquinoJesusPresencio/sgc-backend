package com.condominios.sgc.application.usecase;

import com.condominios.sgc.domain.model.LogAccesoVehicularModel;

public interface RegistrarSalidaVehiculoUseCase {
    LogAccesoVehicularModel ejecutar(Long logId);
}

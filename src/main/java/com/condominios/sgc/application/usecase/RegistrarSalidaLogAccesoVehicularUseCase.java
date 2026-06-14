package com.condominios.sgc.application.usecase;

import com.condominios.sgc.application.dto.response.LogAccesoVehicularResponse;

public interface RegistrarSalidaLogAccesoVehicularUseCase {
    LogAccesoVehicularResponse ejecutar(Long id);
}

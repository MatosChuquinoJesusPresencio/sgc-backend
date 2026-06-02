package com.condominios.sgc.application.usecase;

import com.condominios.sgc.application.dto.RegistrarEntradaRequest;
import com.condominios.sgc.domain.model.LogAccesoVehicularModel;

public interface RegistrarEntradaVehiculoUseCase {
    LogAccesoVehicularModel ejecutar(RegistrarEntradaRequest request);
}

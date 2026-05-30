package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.usecase.ObtenerLogAccesoUseCase;
import com.condominios.sgc.domain.exception.LogAccesoVehicularException;
import com.condominios.sgc.domain.model.LogAccesoVehicularModel;
import com.condominios.sgc.domain.port.LogAccesoVehicularPort;

public class ObtenerLogAccesoUseCaseImpl implements ObtenerLogAccesoUseCase {

    private final LogAccesoVehicularPort logAccesoVehicularPort;

    public ObtenerLogAccesoUseCaseImpl(LogAccesoVehicularPort logAccesoVehicularPort) {
        this.logAccesoVehicularPort = logAccesoVehicularPort;
    }

    @Override
    public LogAccesoVehicularModel ejecutar(Long id) {
        return logAccesoVehicularPort.findById(id)
                .orElseThrow(() -> LogAccesoVehicularException.noEncontrado(id));
    }
}

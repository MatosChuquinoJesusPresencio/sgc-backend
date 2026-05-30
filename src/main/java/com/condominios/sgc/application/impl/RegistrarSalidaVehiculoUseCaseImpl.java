package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.usecase.RegistrarSalidaVehiculoUseCase;
import com.condominios.sgc.domain.exception.LogAccesoVehicularException;
import com.condominios.sgc.domain.model.LogAccesoVehicularModel;
import com.condominios.sgc.domain.port.LogAccesoVehicularPort;

public class RegistrarSalidaVehiculoUseCaseImpl implements RegistrarSalidaVehiculoUseCase {

    private final LogAccesoVehicularPort logAccesoVehicularPort;

    public RegistrarSalidaVehiculoUseCaseImpl(LogAccesoVehicularPort logAccesoVehicularPort) {
        this.logAccesoVehicularPort = logAccesoVehicularPort;
    }

    @Override
    public LogAccesoVehicularModel ejecutar(Long logId) {
        LogAccesoVehicularModel log = logAccesoVehicularPort.findById(logId)
                .orElseThrow(() -> LogAccesoVehicularException.noEncontrado(logId));

        log.registrarSalida();

        return logAccesoVehicularPort.save(log);
    }
}

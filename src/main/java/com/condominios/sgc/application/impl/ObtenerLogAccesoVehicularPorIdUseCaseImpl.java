package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.dto.response.LogAccesoVehicularResponse;
import com.condominios.sgc.application.usecase.ObtenerLogAccesoVehicularPorIdUseCase;
import com.condominios.sgc.domain.exception.LogAccesoVehicularException;
import com.condominios.sgc.domain.port.LogAccesoVehicularPort;

public class ObtenerLogAccesoVehicularPorIdUseCaseImpl implements ObtenerLogAccesoVehicularPorIdUseCase {
    private final LogAccesoVehicularPort logAccesoVehicularPort;

    public ObtenerLogAccesoVehicularPorIdUseCaseImpl(LogAccesoVehicularPort logAccesoVehicularPort) {
        this.logAccesoVehicularPort = logAccesoVehicularPort;
    }

    @Override
    public LogAccesoVehicularResponse ejecutar(Long id) {
        return logAccesoVehicularPort.obtenerPorId(id)
            .map(LogAccesoVehicularResponse::desdeModelo)
            .orElseThrow(LogAccesoVehicularException::noEncontrado);
    }
}

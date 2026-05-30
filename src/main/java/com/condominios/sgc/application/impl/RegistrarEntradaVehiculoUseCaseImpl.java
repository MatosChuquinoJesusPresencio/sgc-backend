package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.dto.RegistrarEntradaRequest;
import com.condominios.sgc.application.usecase.RegistrarEntradaVehiculoUseCase;
import com.condominios.sgc.domain.model.LogAccesoVehicularModel;
import com.condominios.sgc.domain.port.LogAccesoVehicularPort;

public class RegistrarEntradaVehiculoUseCaseImpl implements RegistrarEntradaVehiculoUseCase {

    private final LogAccesoVehicularPort logAccesoVehicularPort;

    public RegistrarEntradaVehiculoUseCaseImpl(LogAccesoVehicularPort logAccesoVehicularPort) {
        this.logAccesoVehicularPort = logAccesoVehicularPort;
    }

    @Override
    public LogAccesoVehicularModel ejecutar(RegistrarEntradaRequest request) {
        LogAccesoVehicularModel modelo = new LogAccesoVehicularModel(
                request.placa(),
                request.ocupante(),
                request.datosInquilino(),
                request.metodo(),
                request.vehiculoId(),
                request.estacionamientoId());

        return logAccesoVehicularPort.save(modelo);
    }
}

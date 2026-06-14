package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.dto.command.CrearVehiculoCommand;
import com.condominios.sgc.application.dto.response.VehiculoResponse;
import com.condominios.sgc.application.usecase.CrearVehiculoUseCase;
import com.condominios.sgc.domain.model.VehiculoModel;
import com.condominios.sgc.domain.port.VehiculoPort;

public class CrearVehiculoUseCaseImpl implements CrearVehiculoUseCase {
    private final VehiculoPort vehiculoPort;

    public CrearVehiculoUseCaseImpl(VehiculoPort vehiculoPort) {
        this.vehiculoPort = vehiculoPort;
    }

    @Override
    public VehiculoResponse ejecutar(CrearVehiculoCommand command) {
        VehiculoModel vehiculo = new VehiculoModel(
            command.marca(), command.color(), command.modelo(),
            command.placa(), command.tipo());
        vehiculo = vehiculoPort.guardar(vehiculo);
        return VehiculoResponse.desdeModelo(vehiculo);
    }
}

package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.dto.CrearVehiculoRequest;
import com.condominios.sgc.application.usecase.CrearVehiculoUseCase;
import com.condominios.sgc.domain.model.VehiculoModel;
import com.condominios.sgc.domain.port.VehiculoPort;

public class CrearVehiculoUseCaseImpl implements CrearVehiculoUseCase {

    private final VehiculoPort vehiculoPort;

    public CrearVehiculoUseCaseImpl(VehiculoPort vehiculoPort) {
        this.vehiculoPort = vehiculoPort;
    }

    @Override
    public VehiculoModel ejecutar(CrearVehiculoRequest request) {
        VehiculoModel modelo = new VehiculoModel(
                request.marca(),
                request.color(),
                request.modelo(),
                request.placa(),
                request.tipo(),
                request.propietarioId(),
                request.inquilinoId(),
                request.estacionamientoId());

        return vehiculoPort.save(modelo);
    }
}

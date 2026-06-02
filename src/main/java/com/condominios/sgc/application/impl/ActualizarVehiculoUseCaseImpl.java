package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.dto.ActualizarVehiculoRequest;
import com.condominios.sgc.application.usecase.ActualizarVehiculoUseCase;
import com.condominios.sgc.domain.exception.VehiculoException;
import com.condominios.sgc.domain.model.VehiculoModel;
import com.condominios.sgc.domain.port.VehiculoPort;

public class ActualizarVehiculoUseCaseImpl implements ActualizarVehiculoUseCase {

    private final VehiculoPort vehiculoPort;

    public ActualizarVehiculoUseCaseImpl(VehiculoPort vehiculoPort) {
        this.vehiculoPort = vehiculoPort;
    }

    @Override
    public VehiculoModel ejecutar(Long id, ActualizarVehiculoRequest request) {
        VehiculoModel modelo = vehiculoPort.findById(id)
                .orElseThrow(() -> VehiculoException.noEncontrado(id));
        modelo.actualizarDatos(request.color());
        return vehiculoPort.save(modelo);
    }
}

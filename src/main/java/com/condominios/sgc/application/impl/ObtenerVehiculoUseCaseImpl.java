package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.usecase.ObtenerVehiculoUseCase;
import com.condominios.sgc.domain.exception.VehiculoException;
import com.condominios.sgc.domain.model.VehiculoModel;
import com.condominios.sgc.domain.port.VehiculoPort;

public class ObtenerVehiculoUseCaseImpl implements ObtenerVehiculoUseCase {

    private final VehiculoPort vehiculoPort;

    public ObtenerVehiculoUseCaseImpl(VehiculoPort vehiculoPort) {
        this.vehiculoPort = vehiculoPort;
    }

    @Override
    public VehiculoModel ejecutar(Long id) {
        return vehiculoPort.findById(id)
                .orElseThrow(() -> VehiculoException.noEncontrado(id));
    }
}

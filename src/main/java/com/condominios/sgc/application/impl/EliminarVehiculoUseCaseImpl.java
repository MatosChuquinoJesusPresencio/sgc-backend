package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.usecase.EliminarVehiculoUseCase;
import com.condominios.sgc.domain.exception.VehiculoException;
import com.condominios.sgc.domain.port.VehiculoPort;

public class EliminarVehiculoUseCaseImpl implements EliminarVehiculoUseCase {

    private final VehiculoPort vehiculoPort;

    public EliminarVehiculoUseCaseImpl(VehiculoPort vehiculoPort) {
        this.vehiculoPort = vehiculoPort;
    }

    @Override
    public void eliminar(Long id) {
        vehiculoPort.findById(id)
                .orElseThrow(() -> VehiculoException.vehiculoNoExistePorId(id));
        vehiculoPort.deleteById(id);
    }
}

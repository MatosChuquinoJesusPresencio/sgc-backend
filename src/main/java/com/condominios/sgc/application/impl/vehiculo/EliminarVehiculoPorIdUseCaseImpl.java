package com.condominios.sgc.application.impl.vehiculo;

import com.condominios.sgc.application.usecase.vehiculo.EliminarVehiculoPorIdUseCase;
import com.condominios.sgc.domain.exception.VehiculoException;
import com.condominios.sgc.domain.port.VehiculoPort;

public class EliminarVehiculoPorIdUseCaseImpl implements EliminarVehiculoPorIdUseCase {
    private final VehiculoPort vehiculoPort;

    public EliminarVehiculoPorIdUseCaseImpl(VehiculoPort vehiculoPort) {
        this.vehiculoPort = vehiculoPort;
    }

    @Override
    public void ejecutar(Long id) {
        vehiculoPort.obtenerPorId(id)
            .orElseThrow(VehiculoException::noEncontrado);
        vehiculoPort.eliminarPorId(id);
    }
}

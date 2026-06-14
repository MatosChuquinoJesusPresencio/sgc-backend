package com.condominios.sgc.application.impl.vehiculo;

import com.condominios.sgc.application.dto.response.VehiculoResponse;
import com.condominios.sgc.application.usecase.vehiculo.ObtenerVehiculoPorIdUseCase;
import com.condominios.sgc.domain.exception.VehiculoException;
import com.condominios.sgc.domain.port.VehiculoPort;

public class ObtenerVehiculoPorIdUseCaseImpl implements ObtenerVehiculoPorIdUseCase {
    private final VehiculoPort vehiculoPort;

    public ObtenerVehiculoPorIdUseCaseImpl(VehiculoPort vehiculoPort) {
        this.vehiculoPort = vehiculoPort;
    }

    @Override
    public VehiculoResponse ejecutar(Long id) {
        return vehiculoPort.obtenerPorId(id)
            .map(VehiculoResponse::desdeModelo)
            .orElseThrow(VehiculoException::noEncontrado);
    }
}

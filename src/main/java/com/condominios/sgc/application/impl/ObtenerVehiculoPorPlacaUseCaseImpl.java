package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.dto.response.VehiculoResponse;
import com.condominios.sgc.application.usecase.ObtenerVehiculoPorPlacaUseCase;
import com.condominios.sgc.domain.exception.VehiculoException;
import com.condominios.sgc.domain.port.VehiculoPort;

public class ObtenerVehiculoPorPlacaUseCaseImpl implements ObtenerVehiculoPorPlacaUseCase {
    private final VehiculoPort vehiculoPort;

    public ObtenerVehiculoPorPlacaUseCaseImpl(VehiculoPort vehiculoPort) {
        this.vehiculoPort = vehiculoPort;
    }

    @Override
    public VehiculoResponse ejecutar(String placa) {
        return vehiculoPort.obtenerPorPlaca(placa)
            .map(VehiculoResponse::desdeModelo)
            .orElseThrow(VehiculoException::noEncontrado);
    }
}

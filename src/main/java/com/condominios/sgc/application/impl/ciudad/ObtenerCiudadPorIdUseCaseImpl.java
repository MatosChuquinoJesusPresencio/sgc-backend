package com.condominios.sgc.application.impl.ciudad;

import com.condominios.sgc.application.dto.response.CiudadResponse;
import com.condominios.sgc.application.usecase.ciudad.ObtenerCiudadPorIdUseCase;
import com.condominios.sgc.domain.exception.CiudadException;
import com.condominios.sgc.domain.port.CiudadPort;

public class ObtenerCiudadPorIdUseCaseImpl implements ObtenerCiudadPorIdUseCase {
    private final CiudadPort ciudadPort;

    public ObtenerCiudadPorIdUseCaseImpl(CiudadPort ciudadPort) {
        this.ciudadPort = ciudadPort;
    }

    @Override
    public CiudadResponse ejecutar(Long id) {
        return ciudadPort.obtenerPorId(id)
            .map(CiudadResponse::desdeModelo)
            .orElseThrow(CiudadException::noEncontrado);
    }
}

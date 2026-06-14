package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.dto.response.TorreResponse;
import com.condominios.sgc.application.usecase.ObtenerTorrePorIdUseCase;
import com.condominios.sgc.domain.exception.TorreException;
import com.condominios.sgc.domain.port.TorrePort;

public class ObtenerTorrePorIdUseCaseImpl implements ObtenerTorrePorIdUseCase {
    private final TorrePort torrePort;

    public ObtenerTorrePorIdUseCaseImpl(TorrePort torrePort) {
        this.torrePort = torrePort;
    }

    @Override
    public TorreResponse ejecutar(Long id) {
        return torrePort.obtenerPorId(id)
            .map(TorreResponse::desdeModelo)
            .orElseThrow(TorreException::noEncontrado);
    }
}

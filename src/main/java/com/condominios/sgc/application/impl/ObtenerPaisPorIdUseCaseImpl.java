package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.dto.response.PaisResponse;
import com.condominios.sgc.application.usecase.ObtenerPaisPorIdUseCase;
import com.condominios.sgc.domain.exception.PaisException;
import com.condominios.sgc.domain.port.PaisPort;

public class ObtenerPaisPorIdUseCaseImpl implements ObtenerPaisPorIdUseCase {
    private final PaisPort paisPort;

    public ObtenerPaisPorIdUseCaseImpl(PaisPort paisPort) {
        this.paisPort = paisPort;
    }

    @Override
    public PaisResponse ejecutar(Long id) {
        return paisPort.obtenerPorId(id)
            .map(PaisResponse::desdeModelo)
            .orElseThrow(PaisException::noEncontrado);
    }
}

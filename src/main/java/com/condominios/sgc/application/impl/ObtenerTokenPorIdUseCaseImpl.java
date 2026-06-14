package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.dto.response.TokenResponse;
import com.condominios.sgc.application.usecase.ObtenerTokenPorIdUseCase;
import com.condominios.sgc.domain.exception.TokenException;
import com.condominios.sgc.domain.port.TokenPort;

public class ObtenerTokenPorIdUseCaseImpl implements ObtenerTokenPorIdUseCase {
    private final TokenPort tokenPort;

    public ObtenerTokenPorIdUseCaseImpl(TokenPort tokenPort) {
        this.tokenPort = tokenPort;
    }

    @Override
    public TokenResponse ejecutar(Long id) {
        return tokenPort.obtenerPorId(id)
            .map(TokenResponse::desdeModelo)
            .orElseThrow(TokenException::noEncontrado);
    }
}

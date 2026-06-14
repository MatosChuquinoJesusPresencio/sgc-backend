package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.dto.response.TokenResponse;
import com.condominios.sgc.application.usecase.ObtenerTokenPorTokenUseCase;
import com.condominios.sgc.domain.exception.TokenException;
import com.condominios.sgc.domain.port.TokenPort;

public class ObtenerTokenPorTokenUseCaseImpl implements ObtenerTokenPorTokenUseCase {
    private final TokenPort tokenPort;

    public ObtenerTokenPorTokenUseCaseImpl(TokenPort tokenPort) {
        this.tokenPort = tokenPort;
    }

    @Override
    public TokenResponse ejecutar(String token) {
        return tokenPort.obtenerPorToken(token)
            .map(TokenResponse::desdeModelo)
            .orElseThrow(TokenException::noEncontrado);
    }
}

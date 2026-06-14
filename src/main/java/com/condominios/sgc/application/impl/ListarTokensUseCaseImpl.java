package com.condominios.sgc.application.impl;

import java.util.List;

import com.condominios.sgc.application.dto.response.TokenResponse;
import com.condominios.sgc.application.usecase.ListarTokensUseCase;
import com.condominios.sgc.domain.port.TokenPort;

public class ListarTokensUseCaseImpl implements ListarTokensUseCase {
    private final TokenPort tokenPort;

    public ListarTokensUseCaseImpl(TokenPort tokenPort) {
        this.tokenPort = tokenPort;
    }

    @Override
    public List<TokenResponse> ejecutar() {
        return tokenPort.obtenerTodos().stream()
            .map(TokenResponse::desdeModelo).toList();
    }
}

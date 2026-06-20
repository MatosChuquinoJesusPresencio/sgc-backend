package com.condominios.sgc.application.service;

import com.condominios.sgc.application.port.in.CerrarSesionUseCase;
import com.condominios.sgc.application.port.out.TokenRepositoryPort;

public class CerrarSesionService implements CerrarSesionUseCase {

    private final TokenRepositoryPort tokenRepository;

    public CerrarSesionService(TokenRepositoryPort tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void ejecutar(String tokenRefresco) {
        tokenRepository.obtenerPorToken(tokenRefresco)
            .ifPresent(tokenRepository::eliminar);
    }
}

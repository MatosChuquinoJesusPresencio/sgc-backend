package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.dto.command.CerrarSesionCommand;
import com.condominios.sgc.application.port.in.CerrarSesionUseCase;
import com.condominios.sgc.application.port.out.TokenRepositoryPort;

public class CerrarSesionUseCaseImpl implements CerrarSesionUseCase {

    private final TokenRepositoryPort tokenRepo;

    public CerrarSesionUseCaseImpl(TokenRepositoryPort tokenRepo) {
        this.tokenRepo = tokenRepo;
    }

    @Override
    public void ejecutar(CerrarSesionCommand command) {
        tokenRepo.buscarPorToken(command.tokenRefresco()).ifPresent(token -> {
            token.usar();
            tokenRepo.guardar(token);
        });
    }
}

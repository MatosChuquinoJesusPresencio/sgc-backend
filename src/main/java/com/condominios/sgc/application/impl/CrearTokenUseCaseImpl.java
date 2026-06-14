package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.dto.command.CrearTokenCommand;
import com.condominios.sgc.application.dto.response.TokenResponse;
import com.condominios.sgc.application.usecase.CrearTokenUseCase;
import com.condominios.sgc.domain.model.TokenModel;
import com.condominios.sgc.domain.port.TokenPort;

public class CrearTokenUseCaseImpl implements CrearTokenUseCase {
    private final TokenPort tokenPort;

    public CrearTokenUseCaseImpl(TokenPort tokenPort) {
        this.tokenPort = tokenPort;
    }

    @Override
    public TokenResponse ejecutar(CrearTokenCommand command) {
        TokenModel token = new TokenModel(
            command.tipo(), command.token(), command.expiracion(), command.idUsuario());

        token = tokenPort.guardar(token);

        return TokenResponse.desdeModelo(token);
    }
}

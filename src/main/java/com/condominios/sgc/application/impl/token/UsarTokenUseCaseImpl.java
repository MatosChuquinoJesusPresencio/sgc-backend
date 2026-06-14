package com.condominios.sgc.application.impl.token;

import com.condominios.sgc.application.dto.response.TokenResponse;
import com.condominios.sgc.application.usecase.token.UsarTokenUseCase;
import com.condominios.sgc.domain.exception.TokenException;
import com.condominios.sgc.domain.model.TokenModel;
import com.condominios.sgc.domain.port.TokenPort;

public class UsarTokenUseCaseImpl implements UsarTokenUseCase {
    private final TokenPort tokenPort;

    public UsarTokenUseCaseImpl(TokenPort tokenPort) {
        this.tokenPort = tokenPort;
    }

    @Override
    public TokenResponse ejecutar(String token) {
        TokenModel tokenModel = tokenPort.obtenerPorToken(token)
            .orElseThrow(TokenException::noEncontrado);

        tokenModel.usar();

        tokenModel = tokenPort.guardar(tokenModel);

        return TokenResponse.desdeModelo(tokenModel);
    }
}

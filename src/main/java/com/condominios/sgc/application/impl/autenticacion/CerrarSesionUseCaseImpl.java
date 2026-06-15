package com.condominios.sgc.application.impl.autenticacion;

import com.condominios.sgc.application.usecase.autenticacion.CerrarSesionUseCase;
import com.condominios.sgc.domain.port.TokenPort;

public class CerrarSesionUseCaseImpl implements CerrarSesionUseCase {
    private final TokenPort tokenPort;

    public CerrarSesionUseCaseImpl(TokenPort tokenPort) {
        this.tokenPort = tokenPort;
    }

    @Override
    public void ejecutar(String accessToken, String refreshToken) {
        if (accessToken != null)
            tokenPort.eliminarPorToken(accessToken);
        if (refreshToken != null)
            tokenPort.eliminarPorToken(refreshToken);
    }
}

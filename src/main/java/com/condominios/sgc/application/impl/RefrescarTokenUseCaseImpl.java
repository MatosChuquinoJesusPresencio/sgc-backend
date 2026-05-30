package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.usecase.RefrescarTokenUseCase;
import com.condominios.sgc.domain.auxiliar.SesionUsuario;
import com.condominios.sgc.domain.port.AutenticacionPort;

public class RefrescarTokenUseCaseImpl implements RefrescarTokenUseCase {

    private final AutenticacionPort autenticacionPort;

    public RefrescarTokenUseCaseImpl(AutenticacionPort autenticacionPort) {
        this.autenticacionPort = autenticacionPort;
    }

    @Override
    public SesionUsuario ejecutar(String refreshToken) {
        return autenticacionPort.refreshToken(refreshToken);
    }
}

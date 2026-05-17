package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.usecase.CerrarSesionUseCase;
import com.condominios.sgc.domain.port.AutenticacionPort;

public class CerrarSesionUseCaseImpl implements CerrarSesionUseCase {

    private final AutenticacionPort autenticacionPort;

    public CerrarSesionUseCaseImpl(AutenticacionPort autenticacionPort) {
        this.autenticacionPort = autenticacionPort;
    }

    @Override
    public void ejecutar(String accessToken) {
        autenticacionPort.cerrarSesion(accessToken);
    }
}

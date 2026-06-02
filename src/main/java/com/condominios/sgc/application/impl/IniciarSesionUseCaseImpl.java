package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.usecase.IniciarSesionUseCase;
import com.condominios.sgc.domain.auxiliar.LoginCompleta;
import com.condominios.sgc.domain.port.AutenticacionPort;

public class IniciarSesionUseCaseImpl implements IniciarSesionUseCase {

    private final AutenticacionPort autenticacionPort;

    public IniciarSesionUseCaseImpl(AutenticacionPort autenticacionPort) {
        this.autenticacionPort = autenticacionPort;
    }

    @Override
    public LoginCompleta ejecutar(String email, String password, boolean rememberMe) {
        return autenticacionPort.login(email, password, rememberMe);
    }
}

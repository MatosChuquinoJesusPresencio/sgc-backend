package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.usecase.RestablecerContrasenaUseCase;
import com.condominios.sgc.domain.port.AutenticacionPort;

public class RestablecerContrasenaUseCaseImpl implements RestablecerContrasenaUseCase {

    private final AutenticacionPort autenticacionPort;

    public RestablecerContrasenaUseCaseImpl(AutenticacionPort autenticacionPort) {
        this.autenticacionPort = autenticacionPort;
    }

    @Override
    public void ejecutar(String token, String nuevaContrasena) {
        autenticacionPort.restablecerContrasena(token, nuevaContrasena);
    }
}

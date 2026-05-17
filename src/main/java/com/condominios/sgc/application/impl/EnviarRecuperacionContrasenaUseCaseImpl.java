package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.usecase.EnviarRecuperacionContrasenaUseCase;
import com.condominios.sgc.domain.port.AutenticacionPort;

public class EnviarRecuperacionContrasenaUseCaseImpl implements EnviarRecuperacionContrasenaUseCase {

    private final AutenticacionPort autenticacionPort;

    public EnviarRecuperacionContrasenaUseCaseImpl(AutenticacionPort autenticacionPort) {
        this.autenticacionPort = autenticacionPort;
    }

    @Override
    public void ejecutar(String email) {
        autenticacionPort.enviarRecuperacionContrasena(email);
    }
}

package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.usecase.EnviarCorreoRecuperacionUseCase;
import com.condominios.sgc.domain.port.CorreoPort;

public class EnviarCorreoRecuperacionUseCaseImpl implements EnviarCorreoRecuperacionUseCase {

    private final CorreoPort correoPort;

    public EnviarCorreoRecuperacionUseCaseImpl(CorreoPort correoPort) {
        this.correoPort = correoPort;
    }

    @Override
    public void ejecutar(String to, String token) {
        correoPort.sendPasswordResetEmail(to, token);
    }
}

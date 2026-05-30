package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.usecase.EnviarCorreoVerificacionUseCase;
import com.condominios.sgc.domain.port.CorreoPort;

public class EnviarCorreoVerificacionUseCaseImpl implements EnviarCorreoVerificacionUseCase {

    private final CorreoPort correoPort;

    public EnviarCorreoVerificacionUseCaseImpl(CorreoPort correoPort) {
        this.correoPort = correoPort;
    }

    @Override
    public void ejecutar(String to, String token) {
        correoPort.sendVerificationEmail(to, token);
    }
}

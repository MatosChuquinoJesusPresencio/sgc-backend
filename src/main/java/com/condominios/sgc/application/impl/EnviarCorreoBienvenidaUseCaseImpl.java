package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.usecase.EnviarCorreoBienvenidaUseCase;
import com.condominios.sgc.domain.port.CorreoPort;

public class EnviarCorreoBienvenidaUseCaseImpl implements EnviarCorreoBienvenidaUseCase {

    private final CorreoPort correoPort;

    public EnviarCorreoBienvenidaUseCaseImpl(CorreoPort correoPort) {
        this.correoPort = correoPort;
    }

    @Override
    public void ejecutar(String to, String nombres, String contrasena) {
        correoPort.sendWelcomeEmail(to, nombres, contrasena);
    }
}

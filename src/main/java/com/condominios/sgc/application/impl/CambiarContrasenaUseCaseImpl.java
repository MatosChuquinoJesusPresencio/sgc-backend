package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.usecase.CambiarContrasenaUseCase;
import com.condominios.sgc.domain.auxiliar.SesionUsuario;
import com.condominios.sgc.domain.port.AutenticacionPort;

public class CambiarContrasenaUseCaseImpl implements CambiarContrasenaUseCase {

    private final AutenticacionPort autenticacionPort;

    public CambiarContrasenaUseCaseImpl(AutenticacionPort autenticacionPort) {
        this.autenticacionPort = autenticacionPort;
    }

    @Override
    public void ejecutar(String email, String currentPassword, String newPassword) {
        SesionUsuario sesion = autenticacionPort.iniciarSesion(email, currentPassword);
        autenticacionPort.cambiarContrasena(sesion.accessToken(), newPassword);
    }
}

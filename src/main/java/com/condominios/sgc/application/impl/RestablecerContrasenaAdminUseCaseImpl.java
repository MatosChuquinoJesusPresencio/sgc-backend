package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.usecase.RestablecerContrasenaAdminUseCase;
import com.condominios.sgc.domain.port.AutenticacionPort;

public class RestablecerContrasenaAdminUseCaseImpl implements RestablecerContrasenaAdminUseCase {

    private final AutenticacionPort autenticacionPort;

    public RestablecerContrasenaAdminUseCaseImpl(AutenticacionPort autenticacionPort) {
        this.autenticacionPort = autenticacionPort;
    }

    @Override
    public void ejecutar(String usuarioId, String nuevaContrasena) {
        autenticacionPort.actualizarPasswordAdmin(usuarioId, nuevaContrasena);
    }
}

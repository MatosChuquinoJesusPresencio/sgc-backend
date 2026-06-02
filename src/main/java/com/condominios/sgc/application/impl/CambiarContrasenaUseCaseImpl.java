package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.usecase.CambiarContrasenaUseCase;
import com.condominios.sgc.domain.exception.AutenticacionException;
import com.condominios.sgc.domain.port.AutenticacionPort;
import com.condominios.sgc.domain.port.UsuarioPort;

public class CambiarContrasenaUseCaseImpl implements CambiarContrasenaUseCase {

    private final AutenticacionPort autenticacionPort;
    private final UsuarioPort usuarioPort;

    public CambiarContrasenaUseCaseImpl(AutenticacionPort autenticacionPort, UsuarioPort usuarioPort) {
        this.autenticacionPort = autenticacionPort;
        this.usuarioPort = usuarioPort;
    }

    @Override
    public void ejecutar(Long userId, String currentPassword, String newPassword) {
        var usuario = usuarioPort.findById(userId)
            .orElseThrow(AutenticacionException::usuarioNoRegistrado);
        autenticacionPort.login(usuario.getCorreo(), currentPassword, false);
        autenticacionPort.changePassword(userId, newPassword);
    }
}

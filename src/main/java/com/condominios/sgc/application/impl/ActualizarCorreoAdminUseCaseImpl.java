package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.usecase.ActualizarCorreoAdminUseCase;
import com.condominios.sgc.domain.exception.UsuarioException;
import com.condominios.sgc.domain.model.UsuarioModel;
import com.condominios.sgc.domain.port.AutenticacionPort;
import com.condominios.sgc.domain.port.UsuarioPort;

public class ActualizarCorreoAdminUseCaseImpl implements ActualizarCorreoAdminUseCase {

    private final UsuarioPort usuarioPort;
    private final AutenticacionPort autenticacionPort;

    public ActualizarCorreoAdminUseCaseImpl(UsuarioPort usuarioPort, AutenticacionPort autenticacionPort) {
        this.usuarioPort = usuarioPort;
        this.autenticacionPort = autenticacionPort;
    }

    @Override
    public UsuarioModel ejecutar(String id, String nuevoCorreo) {
        var usuario = usuarioPort.findById(id)
            .orElseThrow(() -> UsuarioException.noEncontrada());

        if (usuarioPort.existsByCorreo(nuevoCorreo)) {
            throw UsuarioException.correoYaEnUso();
        }

        autenticacionPort.actualizarEmailAdmin(id, nuevoCorreo);

        usuario.actualizarCorreo(nuevoCorreo);

        return usuarioPort.save(usuario);
    }
}

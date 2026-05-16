package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.usecase.ActualizarCorreoUseCase;
import com.condominios.sgc.domain.exception.UsuarioException;
import com.condominios.sgc.domain.model.UsuarioModel;
import com.condominios.sgc.domain.port.AutenticacionPort;
import com.condominios.sgc.domain.port.UsuarioPort;

public class ActualizarCorreoUseCaseImpl implements ActualizarCorreoUseCase {

    private final UsuarioPort usuarioPort;
    private final AutenticacionPort autenticacionPort;

    public ActualizarCorreoUseCaseImpl(UsuarioPort usuarioPort, AutenticacionPort autenticacionPort) {
        this.usuarioPort = usuarioPort;
        this.autenticacionPort = autenticacionPort;
    }

    @Override
    public UsuarioModel ejecutar(String id, String accessToken, String nuevoCorreo) {
        var usuario = usuarioPort.findById(id)
            .orElseThrow(() -> UsuarioException.noEncontrada());

        if (usuarioPort.existsByCorreo(nuevoCorreo)) {
            throw UsuarioException.correoYaEnUso();
        }

        autenticacionPort.actualizarEmail(accessToken, nuevoCorreo);

        usuario.actualizarCorreo(nuevoCorreo);

        return usuarioPort.save(usuario);
    }
}

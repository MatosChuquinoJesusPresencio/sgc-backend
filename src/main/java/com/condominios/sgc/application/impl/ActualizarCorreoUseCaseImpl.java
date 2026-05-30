package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.usecase.ActualizarCorreoUseCase;
import com.condominios.sgc.domain.exception.UsuarioException;
import com.condominios.sgc.domain.model.UsuarioModel;
import com.condominios.sgc.domain.model.VerificacionTokenModel;
import com.condominios.sgc.domain.port.CorreoPort;
import com.condominios.sgc.domain.port.UsuarioPort;
import com.condominios.sgc.domain.port.VerificacionTokenPort;

public class ActualizarCorreoUseCaseImpl implements ActualizarCorreoUseCase {

    private final UsuarioPort usuarioPort;
    private final VerificacionTokenPort verificacionTokenPort;
    private final CorreoPort correoPort;

    public ActualizarCorreoUseCaseImpl(
            UsuarioPort usuarioPort,
            VerificacionTokenPort verificacionTokenPort,
            CorreoPort correoPort) {
        this.usuarioPort = usuarioPort;
        this.verificacionTokenPort = verificacionTokenPort;
        this.correoPort = correoPort;
    }

    @Override
    public UsuarioModel ejecutar(String id, String nuevoCorreo) {
        var usuario = usuarioPort.findById(id)
            .orElseThrow(UsuarioException::noEncontrado);

        if (usuarioPort.existsByCorreo(nuevoCorreo)) {
            throw UsuarioException.correoYaEnUso();
        }

        var tokenModel = VerificacionTokenModel.crear(id, nuevoCorreo);
        verificacionTokenPort.save(tokenModel);

        correoPort.enviarVerificacionCorreo(nuevoCorreo, tokenModel.getToken());

        usuario.cambiarCorreo(nuevoCorreo);

        return usuarioPort.save(usuario);
    }
}

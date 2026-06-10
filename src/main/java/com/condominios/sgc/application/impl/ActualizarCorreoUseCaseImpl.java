package com.condominios.sgc.application.impl;

import java.time.Instant;
import java.util.UUID;

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
    public UsuarioModel ejecutar(Long id, String nuevoCorreo, String verificationToken) {
        var usuario = usuarioPort.findById(id)
            .orElseThrow(UsuarioException::noEncontrado);

        if (usuarioPort.existsByCorreo(nuevoCorreo)) {
            throw UsuarioException.correoYaEnUso();
        }

        var tokenValue = UUID.randomUUID().toString();

        var tokenModel = new VerificacionTokenModel(
            UUID.randomUUID().toString(),
            id,
            nuevoCorreo,
            tokenValue,
            Instant.now().plusSeconds(3600)
        );
        verificacionTokenPort.save(tokenModel);

        correoPort.sendVerificationEmail(nuevoCorreo, tokenModel.getToken());

        usuario.cambiarCorreo(nuevoCorreo);

        return usuarioPort.save(usuario);
    }
}

package com.condominios.sgc.application.impl;

import java.time.Instant;
import java.util.UUID;

import com.condominios.sgc.application.usecase.ActualizarCorreoUseCase;
import com.condominios.sgc.application.usecase.EnviarCorreoVerificacionUseCase;
import com.condominios.sgc.domain.exception.UsuarioException;
import com.condominios.sgc.domain.model.UsuarioModel;
import com.condominios.sgc.domain.model.VerificacionTokenModel;
import com.condominios.sgc.domain.port.UsuarioPort;
import com.condominios.sgc.domain.port.VerificacionTokenPort;

public class ActualizarCorreoUseCaseImpl implements ActualizarCorreoUseCase {

    private final UsuarioPort usuarioPort;
    private final VerificacionTokenPort verificacionTokenPort;
    private final EnviarCorreoVerificacionUseCase enviarCorreoVerificacionUseCase;

    public ActualizarCorreoUseCaseImpl(
            UsuarioPort usuarioPort,
            VerificacionTokenPort verificacionTokenPort,
            EnviarCorreoVerificacionUseCase enviarCorreoVerificacionUseCase) {
        this.usuarioPort = usuarioPort;
        this.verificacionTokenPort = verificacionTokenPort;
        this.enviarCorreoVerificacionUseCase = enviarCorreoVerificacionUseCase;
    }

    @Override
    public UsuarioModel ejecutar(Long id, String nuevoCorreo) {
        var usuario = usuarioPort.findById(id)
            .orElseThrow(UsuarioException::noEncontrado);

        if (usuarioPort.existsByCorreo(nuevoCorreo)) {
            throw UsuarioException.correoYaEnUso();
        }

        String verificationToken = UUID.randomUUID().toString();

        var tokenModel = new VerificacionTokenModel(
            UUID.randomUUID().toString(),
            id,
            nuevoCorreo,
            verificationToken,
            Instant.now().plusSeconds(3600)
        );
        verificacionTokenPort.save(tokenModel);

        enviarCorreoVerificacionUseCase.ejecutar(nuevoCorreo, verificationToken);

        usuario.cambiarCorreo(nuevoCorreo);

        return usuarioPort.save(usuario);
    }
}

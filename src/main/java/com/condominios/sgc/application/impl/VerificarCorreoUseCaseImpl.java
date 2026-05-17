package com.condominios.sgc.application.impl;

import java.time.Instant;

import com.condominios.sgc.application.usecase.VerificarCorreoUseCase;
import com.condominios.sgc.domain.exception.UsuarioException;
import com.condominios.sgc.domain.model.UsuarioModel;
import com.condominios.sgc.domain.port.AutenticacionPort;
import com.condominios.sgc.domain.port.UsuarioPort;
import com.condominios.sgc.domain.port.VerificacionTokenPort;

public class VerificarCorreoUseCaseImpl implements VerificarCorreoUseCase {

    private final UsuarioPort usuarioPort;
    private final AutenticacionPort autenticacionPort;
    private final VerificacionTokenPort verificacionTokenPort;

    public VerificarCorreoUseCaseImpl(
            UsuarioPort usuarioPort,
            AutenticacionPort autenticacionPort,
            VerificacionTokenPort verificacionTokenPort) {
        this.usuarioPort = usuarioPort;
        this.autenticacionPort = autenticacionPort;
        this.verificacionTokenPort = verificacionTokenPort;
    }

    @Override
    public UsuarioModel ejecutar(String token) {
        var tokenModel = verificacionTokenPort.findByTokenAndUsadoFalse(token)
            .orElseThrow(() -> UsuarioException.correoPendienteNoEncontrado());

        if (tokenModel.getExpiracion().isBefore(Instant.now())) {
            throw UsuarioException.correoNoVerificado();
        }

        var usuario = usuarioPort.findById(tokenModel.getUsuarioId())
            .orElseThrow(UsuarioException::noEncontrado);

        autenticacionPort.actualizarCorreoAdmin(tokenModel.getUsuarioId(), tokenModel.getNuevoCorreo());

        usuario.confirmarCorreo();

        var saved = usuarioPort.save(usuario);

        tokenModel.setUsado(true);
        verificacionTokenPort.save(tokenModel);

        return saved;
    }
}

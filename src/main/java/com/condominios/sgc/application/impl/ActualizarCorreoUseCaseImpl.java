package com.condominios.sgc.application.impl;

import java.time.Instant;
import java.util.UUID;

import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;

import com.condominios.sgc.application.usecase.ActualizarCorreoUseCase;
import com.condominios.sgc.domain.exception.AutenticacionException;
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
    private final JwtDecoder jwtDecoder;

    public ActualizarCorreoUseCaseImpl(
            UsuarioPort usuarioPort,
            VerificacionTokenPort verificacionTokenPort,
            CorreoPort correoPort,
            JwtDecoder jwtDecoder) {
        this.usuarioPort = usuarioPort;
        this.verificacionTokenPort = verificacionTokenPort;
        this.correoPort = correoPort;
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    public UsuarioModel ejecutar(String id, String accessToken, String nuevoCorreo) {
        try {
            var jwt = jwtDecoder.decode(accessToken);
            if (!id.equals(jwt.getSubject())) {
                throw AutenticacionException.errorAutenticacion(
                    "El token no pertenece al usuario especificado");
            }
        } catch (JwtException e) {
            throw AutenticacionException.errorAutenticacion("Token inválido");
        }

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

        correoPort.enviarVerificacionCorreo(nuevoCorreo, verificationToken);

        usuario.pendienteVerificarCorreo(nuevoCorreo);

        return usuarioPort.save(usuario);
    }
}

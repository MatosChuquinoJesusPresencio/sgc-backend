package com.condominios.sgc.application.impl;

import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;

import com.condominios.sgc.application.usecase.ActualizarCorreoUseCase;
import com.condominios.sgc.domain.exception.AutenticacionException;
import com.condominios.sgc.domain.exception.UsuarioException;
import com.condominios.sgc.domain.model.UsuarioModel;
import com.condominios.sgc.domain.port.AutenticacionPort;
import com.condominios.sgc.domain.port.UsuarioPort;

public class ActualizarCorreoUseCaseImpl implements ActualizarCorreoUseCase {

    private final UsuarioPort usuarioPort;
    private final AutenticacionPort autenticacionPort;
    private final JwtDecoder jwtDecoder;

    public ActualizarCorreoUseCaseImpl(UsuarioPort usuarioPort, AutenticacionPort autenticacionPort, JwtDecoder jwtDecoder) {
        this.usuarioPort = usuarioPort;
        this.autenticacionPort = autenticacionPort;
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

        autenticacionPort.actualizarCorreo(accessToken, nuevoCorreo);

        usuario.actualizarCorreo(nuevoCorreo);

        return usuarioPort.save(usuario);
    }
}

package com.condominios.sgc.application.impl.autenticacion;

import java.time.Instant;

import com.condominios.sgc.application.usecase.autenticacion.ConfirmarCorreoUseCase;
import com.condominios.sgc.domain.exception.TokenException;
import com.condominios.sgc.domain.exception.UsuarioException;
import com.condominios.sgc.domain.model.TokenModel;
import com.condominios.sgc.domain.model.UsuarioModel;
import com.condominios.sgc.domain.port.TokenPort;
import com.condominios.sgc.domain.port.UsuarioPort;

public class ConfirmarCorreoUseCaseImpl implements ConfirmarCorreoUseCase {
    private final TokenPort tokenPort;
    private final UsuarioPort usuarioPort;

    public ConfirmarCorreoUseCaseImpl(TokenPort tokenPort, UsuarioPort usuarioPort) {
        this.tokenPort = tokenPort;
        this.usuarioPort = usuarioPort;
    }

    @Override
    public void ejecutar(String token) {
        TokenModel tokenModel = tokenPort.obtenerPorToken(token)
            .orElseThrow(TokenException::noEncontrado);

        if (tokenModel.getExpiracion().isBefore(Instant.now())) {
            throw TokenException.tokenExpirado();
        }

        tokenModel.usar();
        tokenPort.guardar(tokenModel);

        UsuarioModel usuario = usuarioPort.obtenerPorId(tokenModel.getIdUsuario())
            .orElseThrow(UsuarioException::noEncontrado);
        usuario.confirmarCorreo();
        usuarioPort.guardar(usuario);
    }
}

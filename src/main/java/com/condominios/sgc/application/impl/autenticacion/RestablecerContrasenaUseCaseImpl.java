package com.condominios.sgc.application.impl.autenticacion;

import java.time.Instant;

import com.condominios.sgc.application.dto.command.RestablecerContrasenaCommand;
import com.condominios.sgc.application.usecase.autenticacion.RestablecerContrasenaUseCase;
import com.condominios.sgc.domain.exception.TokenException;
import com.condominios.sgc.domain.exception.UsuarioException;
import com.condominios.sgc.domain.model.TokenModel;
import com.condominios.sgc.domain.model.UsuarioModel;
import com.condominios.sgc.domain.port.AutenticacionPort;
import com.condominios.sgc.domain.port.TokenPort;
import com.condominios.sgc.domain.port.UsuarioPort;

public class RestablecerContrasenaUseCaseImpl implements RestablecerContrasenaUseCase {
    private final TokenPort tokenPort;
    private final UsuarioPort usuarioPort;
    private final AutenticacionPort autenticacionPort;

    public RestablecerContrasenaUseCaseImpl(TokenPort tokenPort, UsuarioPort usuarioPort, AutenticacionPort autenticacionPort) {
        this.tokenPort = tokenPort;
        this.usuarioPort = usuarioPort;
        this.autenticacionPort = autenticacionPort;
    }

    @Override
    public void ejecutar(RestablecerContrasenaCommand command) {
        TokenModel tokenModel = tokenPort.obtenerPorToken(command.token())
            .orElseThrow(TokenException::noEncontrado);

        if (tokenModel.getExpiracion().isBefore(Instant.now())) {
            throw TokenException.tokenExpirado();
        }

        tokenModel.usar();
        tokenPort.guardar(tokenModel);

        UsuarioModel usuario = usuarioPort.obtenerPorId(tokenModel.getIdUsuario())
            .orElseThrow(UsuarioException::noEncontrado);
        usuario.cambiarContrasena(autenticacionPort.hashContrasena(command.nuevaContrasena()));
        usuarioPort.guardar(usuario);
    }
}

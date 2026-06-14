package com.condominios.sgc.application.impl.autenticacion;

import com.condominios.sgc.application.dto.command.LoginCommand;
import com.condominios.sgc.application.dto.response.LoginResponse;
import com.condominios.sgc.application.usecase.autenticacion.LoginUseCase;
import com.condominios.sgc.domain.exception.UsuarioException;
import com.condominios.sgc.domain.model.UsuarioModel;
import com.condominios.sgc.domain.port.AutenticacionPort;
import com.condominios.sgc.domain.port.UsuarioPort;

public class LoginUseCaseImpl implements LoginUseCase {
    private final UsuarioPort usuarioPort;
    private final AutenticacionPort autenticacionPort;

    public LoginUseCaseImpl(UsuarioPort usuarioPort, AutenticacionPort autenticacionPort) {
        this.usuarioPort = usuarioPort;
        this.autenticacionPort = autenticacionPort;
    }

    @Override
    public LoginResponse ejecutar(LoginCommand command) {
        UsuarioModel usuario = usuarioPort.obtenerPorCorreo(command.correo())
            .orElseThrow(UsuarioException::noEncontrado);

        if (!autenticacionPort.verificarContrasena(command.contrasena(), usuario.getContrasena())) {
            throw UsuarioException.credencialesInvalidas();
        }

        String token = autenticacionPort.generarToken(usuario.getId(), usuario.getRol().name());

        return new LoginResponse(token, usuario.getId(), usuario.getRol().name(), usuario.getNombres());
    }
}

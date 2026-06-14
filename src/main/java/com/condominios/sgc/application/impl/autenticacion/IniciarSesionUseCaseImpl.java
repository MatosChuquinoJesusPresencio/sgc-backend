package com.condominios.sgc.application.impl.autenticacion;

import com.condominios.sgc.application.dto.command.IniciarSesionCommand;
import com.condominios.sgc.application.dto.response.IniciarSesionResponse;
import com.condominios.sgc.application.usecase.autenticacion.IniciarSesionUseCase;
import com.condominios.sgc.domain.auxiliar.TipoToken;
import com.condominios.sgc.domain.exception.UsuarioException;
import com.condominios.sgc.domain.model.TokenModel;
import com.condominios.sgc.domain.model.UsuarioModel;
import com.condominios.sgc.domain.port.AutenticacionPort;
import com.condominios.sgc.domain.port.TokenPort;
import com.condominios.sgc.domain.port.UsuarioPort;

public class IniciarSesionUseCaseImpl implements IniciarSesionUseCase {
    private final UsuarioPort usuarioPort;
    private final AutenticacionPort autenticacionPort;
    private final TokenPort tokenPort;

    public IniciarSesionUseCaseImpl(UsuarioPort usuarioPort, AutenticacionPort autenticacionPort, TokenPort tokenPort) {
        this.usuarioPort = usuarioPort;
        this.autenticacionPort = autenticacionPort;
        this.tokenPort = tokenPort;
    }

    @Override
    public IniciarSesionResponse ejecutar(IniciarSesionCommand command) {
        UsuarioModel usuario = usuarioPort.obtenerPorCorreo(command.correo())
            .orElseThrow(UsuarioException::noEncontrado);

        if (!autenticacionPort.verificarContrasena(command.contrasena(), usuario.getContrasena())) {
            throw UsuarioException.credencialesInvalidas();
        }

        TokenModel token = tokenPort.generarToken(TipoToken.ACCESS, usuario.getId());

        return new IniciarSesionResponse(token.getToken(), usuario.getId(), usuario.getRol().name(), usuario.getNombres());
    }
}

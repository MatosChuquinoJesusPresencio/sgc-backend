package com.condominios.sgc.application.impl.autenticacion;

import com.condominios.sgc.application.dto.command.RefrescarTokenCommand;
import com.condominios.sgc.application.dto.response.IniciarSesionResponse;
import com.condominios.sgc.application.usecase.autenticacion.RefrescarTokenUseCase;
import com.condominios.sgc.domain.exception.TokenException;
import com.condominios.sgc.domain.exception.UsuarioException;
import com.condominios.sgc.domain.model.UsuarioModel;
import com.condominios.sgc.domain.port.AutenticacionPort;
import com.condominios.sgc.domain.port.UsuarioPort;

public class RefrescarTokenUseCaseImpl implements RefrescarTokenUseCase {
    private final AutenticacionPort autenticacionPort;
    private final UsuarioPort usuarioPort;

    public RefrescarTokenUseCaseImpl(AutenticacionPort autenticacionPort, UsuarioPort usuarioPort) {
        this.autenticacionPort = autenticacionPort;
        this.usuarioPort = usuarioPort;
    }

    @Override
    public IniciarSesionResponse ejecutar(RefrescarTokenCommand command) {
        Long idUsuario = autenticacionPort.validarToken(command.token())
            .orElseThrow(TokenException::noEncontrado);

        UsuarioModel usuario = usuarioPort.obtenerPorId(idUsuario)
            .orElseThrow(UsuarioException::noEncontrado);

        String nuevoToken = autenticacionPort.generarToken(usuario.getId(), usuario.getRol().name());

        return new IniciarSesionResponse(nuevoToken, usuario.getId(), usuario.getRol().name(), usuario.getNombres());
    }
}

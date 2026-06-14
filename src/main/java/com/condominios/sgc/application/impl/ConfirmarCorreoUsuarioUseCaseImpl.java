package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.dto.response.UsuarioResponse;
import com.condominios.sgc.application.usecase.ConfirmarCorreoUsuarioUseCase;
import com.condominios.sgc.domain.exception.UsuarioException;
import com.condominios.sgc.domain.model.UsuarioModel;
import com.condominios.sgc.domain.port.UsuarioPort;

public class ConfirmarCorreoUsuarioUseCaseImpl implements ConfirmarCorreoUsuarioUseCase {
    private final UsuarioPort usuarioPort;

    public ConfirmarCorreoUsuarioUseCaseImpl(UsuarioPort usuarioPort) {
        this.usuarioPort = usuarioPort;
    }

    @Override
    public UsuarioResponse ejecutar(Long id) {
        UsuarioModel usuario = usuarioPort.obtenerPorId(id)
            .orElseThrow(UsuarioException::noEncontrado);
        usuario.confirmarCorreo();
        usuario = usuarioPort.guardar(usuario);
        return UsuarioResponse.desdeModelo(usuario);
    }
}

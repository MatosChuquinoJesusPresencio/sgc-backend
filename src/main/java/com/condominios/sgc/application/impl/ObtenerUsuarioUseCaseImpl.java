package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.usecase.ObtenerUsuarioUseCase;
import com.condominios.sgc.domain.exception.UsuarioException;
import com.condominios.sgc.domain.model.UsuarioModel;
import com.condominios.sgc.domain.port.UsuarioPort;

public class ObtenerUsuarioUseCaseImpl implements ObtenerUsuarioUseCase {

    private final UsuarioPort usuarioPort;

    public ObtenerUsuarioUseCaseImpl(UsuarioPort usuarioPort) {
        this.usuarioPort = usuarioPort;
    }

    @Override
    public UsuarioModel ejecutar(String id) {
        return usuarioPort.findById(id)
            .orElseThrow(UsuarioException::noEncontrada);
    }
}

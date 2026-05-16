package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.usecase.EliminarUsuarioUseCase;
import com.condominios.sgc.domain.exception.UsuarioException;
import com.condominios.sgc.domain.port.UsuarioPort;

public class EliminarUsuarioUseCaseImpl implements EliminarUsuarioUseCase {

    private final UsuarioPort usuarioPort;

    public EliminarUsuarioUseCaseImpl(UsuarioPort usuarioPort) {
        this.usuarioPort = usuarioPort;
    }

    @Override
    public void ejecutar(String id) {
        usuarioPort.findById(id)
            .orElseThrow(UsuarioException::noEncontrada);
        usuarioPort.deleteById(id);
    }
}

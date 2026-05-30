package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.usecase.EliminarUsuarioUseCase;
import com.condominios.sgc.domain.exception.UsuarioException;
import com.condominios.sgc.domain.port.AutenticacionPort;
import com.condominios.sgc.domain.port.UsuarioPort;

public class EliminarUsuarioUseCaseImpl implements EliminarUsuarioUseCase {

    private final UsuarioPort usuarioPort;
    private final AutenticacionPort autenticacionPort;

    public EliminarUsuarioUseCaseImpl(UsuarioPort usuarioPort, AutenticacionPort autenticacionPort) {
        this.usuarioPort = usuarioPort;
        this.autenticacionPort = autenticacionPort;
    }

    @Override
    public void ejecutar(Long id) {
        usuarioPort.findById(id)
            .orElseThrow(UsuarioException::noEncontrado);

        autenticacionPort.deleteUser(id);
        usuarioPort.deleteById(id);
    }
}

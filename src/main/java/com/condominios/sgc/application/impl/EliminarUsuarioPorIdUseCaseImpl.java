package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.usecase.EliminarUsuarioPorIdUseCase;
import com.condominios.sgc.domain.exception.UsuarioException;
import com.condominios.sgc.domain.port.UsuarioPort;

public class EliminarUsuarioPorIdUseCaseImpl implements EliminarUsuarioPorIdUseCase {
    private final UsuarioPort usuarioPort;

    public EliminarUsuarioPorIdUseCaseImpl(UsuarioPort usuarioPort) {
        this.usuarioPort = usuarioPort;
    }

    @Override
    public void ejecutar(Long id) {
        usuarioPort.obtenerPorId(id)
            .orElseThrow(UsuarioException::noEncontrado);
        usuarioPort.eliminarPorId(id);
    }
}

package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.usecase.AsignarCondominioUseCase;
import com.condominios.sgc.domain.exception.UsuarioException;
import com.condominios.sgc.domain.model.UsuarioModel;
import com.condominios.sgc.domain.port.UsuarioPort;

public class AsignarCondominioUseCaseImpl implements AsignarCondominioUseCase {

    private final UsuarioPort usuarioPort;

    public AsignarCondominioUseCaseImpl(UsuarioPort usuarioPort) {
        this.usuarioPort = usuarioPort;
    }

    @Override
    public UsuarioModel ejecutar(Long usuarioId, Long condominioId) {
        UsuarioModel usuario = usuarioPort.findById(usuarioId)
            .orElseThrow(UsuarioException::noEncontrado);

        if (condominioId != null) {
            usuario.asignarCondominio(condominioId);
        } else {
            usuario.desasignarCondominio();
        }

        return usuarioPort.save(usuario);
    }
}

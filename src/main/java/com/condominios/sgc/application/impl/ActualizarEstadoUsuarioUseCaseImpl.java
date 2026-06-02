package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.usecase.ActualizarEstadoUsuarioUseCase;
import com.condominios.sgc.domain.auxiliar.Rol;
import com.condominios.sgc.domain.exception.UsuarioException;
import com.condominios.sgc.domain.model.UsuarioModel;
import com.condominios.sgc.domain.port.UsuarioPort;

public class ActualizarEstadoUsuarioUseCaseImpl implements ActualizarEstadoUsuarioUseCase {

    private final UsuarioPort usuarioPort;

    public ActualizarEstadoUsuarioUseCaseImpl(UsuarioPort usuarioPort) {
        this.usuarioPort = usuarioPort;
    }

    @Override
    public UsuarioModel ejecutar(Long id, Boolean activo, Rol rolAutenticado) {
        if (!rolAutenticado.puedeModificarEstado()) {
            throw UsuarioException.noPermisoParaModificarEstado();
        }

        UsuarioModel usuario = usuarioPort.findById(id)
            .orElseThrow(UsuarioException::noEncontrado);

        usuario.cambiarActivo(activo);

        return usuarioPort.save(usuario);
    }
}

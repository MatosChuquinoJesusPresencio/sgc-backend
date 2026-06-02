package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.dto.ActualizarUsuarioRequest;
import com.condominios.sgc.application.usecase.ActualizarUsuarioUseCase;
import com.condominios.sgc.domain.auxiliar.Rol;
import com.condominios.sgc.domain.exception.UsuarioException;
import com.condominios.sgc.domain.model.UsuarioModel;
import com.condominios.sgc.domain.port.UsuarioPort;

public class ActualizarUsuarioUseCaseImpl implements ActualizarUsuarioUseCase {

    private final UsuarioPort usuarioPort;

    public ActualizarUsuarioUseCaseImpl(UsuarioPort usuarioPort) {
        this.usuarioPort = usuarioPort;
    }

    @Override
    public UsuarioModel ejecutar(Long id, ActualizarUsuarioRequest request, Rol rolAutenticado) {
        if (!rolAutenticado.puedeAsignar(request.rol())) {
            throw UsuarioException.noPermisoParaAsignarRol();
        }

        UsuarioModel usuario = usuarioPort.findById(id)
            .orElseThrow(UsuarioException::noEncontrado);

        usuario.actualizar(
            request.nombres(),
            request.apellidos(),
            request.telefono(),
            request.rol(),
            request.condominioId()
        );

        return usuarioPort.save(usuario);
    }
}

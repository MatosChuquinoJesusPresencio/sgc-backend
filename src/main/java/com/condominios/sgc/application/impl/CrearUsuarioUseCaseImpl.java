package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.dto.CrearUsuarioRequest;
import com.condominios.sgc.application.usecase.CrearUsuarioUseCase;
import com.condominios.sgc.domain.auxiliar.Rol;
import com.condominios.sgc.domain.exception.UsuarioException;
import com.condominios.sgc.domain.model.UsuarioModel;
import com.condominios.sgc.domain.port.AutenticacionPort;
import com.condominios.sgc.domain.port.CorreoPort;
import com.condominios.sgc.domain.port.UsuarioPort;

public class CrearUsuarioUseCaseImpl implements CrearUsuarioUseCase {

    private final AutenticacionPort autenticacionPort;
    private final UsuarioPort usuarioPort;
    private final CorreoPort correoPort;

    public CrearUsuarioUseCaseImpl(AutenticacionPort autenticacionPort, UsuarioPort usuarioPort, CorreoPort correoPort) {
        this.autenticacionPort = autenticacionPort;
        this.usuarioPort = usuarioPort;
        this.correoPort = correoPort;
    }

    @Override
    public UsuarioModel ejecutar(CrearUsuarioRequest request, Rol rolAutenticado) {
        if (!rolAutenticado.puedeAsignar(request.rol())) {
            throw UsuarioException.noPermisoParaAsignarRol();
        }

        if (usuarioPort.existsByCorreo(request.correo())) {
            throw UsuarioException.correoYaEnUso();
        }

        autenticacionPort.createUser(request.correo(), request.contrasena(), request.rol().name());

        var usuario = new UsuarioModel(
            request.nombres(),
            request.apellidos(),
            request.correo(),
            request.telefono(),
            request.rol(),
            request.condominioId(),
            request.contrasena()
        );

        var saved = usuarioPort.save(usuario);

        correoPort.sendWelcomeEmail(request.correo(), request.nombres(), request.contrasena());

        return saved;
    }
}

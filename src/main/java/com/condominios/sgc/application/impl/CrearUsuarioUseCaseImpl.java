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

        if (usuarioPort.existsByCorreo(request.email())) {
            throw UsuarioException.correoYaEnUso();
        }

        correoPort.enviarBienvenida(request.email(), request.nombres(), request.password());

        String userId = autenticacionPort.crearUsuario(
            request.email(), request.password(), request.rol().name());

        var usuario = new UsuarioModel(
            userId,
            request.nombres(),
            request.apellidos(),
            request.email(),
            request.telefono(),
            request.rol(),
            true,
            request.condominioId()
        );
        usuario.asignarCorreoVerificado(true);

        var saved = usuarioPort.save(usuario);

        autenticacionPort.actualizarPasswordAdmin(userId, request.password());

        return saved;
    }
}

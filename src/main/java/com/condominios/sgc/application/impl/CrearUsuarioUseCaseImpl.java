package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.dto.CrearUsuarioRequest;
import com.condominios.sgc.application.usecase.CrearUsuarioUseCase;
import com.condominios.sgc.domain.auxiliar.Rol;
import com.condominios.sgc.domain.exception.UsuarioException;
import com.condominios.sgc.domain.model.UsuarioModel;
import com.condominios.sgc.domain.port.AutenticacionPort;
import com.condominios.sgc.domain.port.CorreoPort;
import com.condominios.sgc.domain.port.UsuarioPort;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CrearUsuarioUseCaseImpl implements CrearUsuarioUseCase {

    private final AutenticacionPort autenticacionPort;
    private final UsuarioPort usuarioPort;
    private final CorreoPort correoPort;
    private final PasswordEncoder passwordEncoder;

    public CrearUsuarioUseCaseImpl(AutenticacionPort autenticacionPort, UsuarioPort usuarioPort, CorreoPort correoPort, PasswordEncoder passwordEncoder) {
        this.autenticacionPort = autenticacionPort;
        this.usuarioPort = usuarioPort;
        this.correoPort = correoPort;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UsuarioModel ejecutar(CrearUsuarioRequest request, Rol rolAutenticado) {
        if (!rolAutenticado.puedeAsignar(request.rol())) {
            throw UsuarioException.noPermisoParaAsignarRol();
        }

        if (usuarioPort.existsByCorreo(request.correo())) {
            throw UsuarioException.correoYaEnUso();
        }

        var hashedPassword = passwordEncoder.encode(request.contrasena());

        autenticacionPort.createUser(request.correo(), hashedPassword, request.rol().name());

        var usuario = new UsuarioModel(
            request.nombres(),
            request.apellidos(),
            request.correo(),
            request.telefono(),
            request.rol(),
            request.condominioId(),
            hashedPassword
        );

        var saved = usuarioPort.save(usuario);

        correoPort.sendWelcomeEmail(saved.getCorreo(), saved.getNombres(), request.contrasena());

        return saved;
    }
}

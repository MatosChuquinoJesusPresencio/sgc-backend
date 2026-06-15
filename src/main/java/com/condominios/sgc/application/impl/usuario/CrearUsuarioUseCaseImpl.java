package com.condominios.sgc.application.impl.usuario;

import java.util.UUID;

import com.condominios.sgc.application.dto.command.CrearUsuarioCommand;
import com.condominios.sgc.application.dto.response.UsuarioResponse;
import com.condominios.sgc.application.usecase.usuario.CrearUsuarioUseCase;
import com.condominios.sgc.domain.exception.UsuarioException;
import com.condominios.sgc.domain.model.UsuarioModel;
import com.condominios.sgc.domain.port.AutenticacionPort;
import com.condominios.sgc.domain.port.CorreoPort;
import com.condominios.sgc.domain.port.UsuarioPort;

public class CrearUsuarioUseCaseImpl implements CrearUsuarioUseCase {
    private final UsuarioPort usuarioPort;
    private final AutenticacionPort autenticacionPort;
    private final CorreoPort correoPort;

    public CrearUsuarioUseCaseImpl(UsuarioPort usuarioPort, AutenticacionPort autenticacionPort, CorreoPort correoPort) {
        this.usuarioPort = usuarioPort;
        this.autenticacionPort = autenticacionPort;
        this.correoPort = correoPort;
    }

    @Override
    public UsuarioResponse ejecutar(CrearUsuarioCommand command) {
        if (!command.rolSolicitante().puedeAsignar(command.rol())) {
            throw UsuarioException.noPermisoAsignarRol();
        }

        String contrasenaPlana = UUID.randomUUID().toString().replace("-", "").substring(0, 12);
        String hash = autenticacionPort.hashContrasena(contrasenaPlana);

        UsuarioModel usuario = new UsuarioModel(
            command.nombres(), command.apellidos(), command.correo(),
            command.telefono(), command.rol(), hash, command.idCondominio());
        usuario = usuarioPort.guardar(usuario);

        correoPort.enviarBienvenida(usuario.getCorreo(), usuario.getNombres(), contrasenaPlana);

        return UsuarioResponse.desdeModelo(usuario);
    }
}

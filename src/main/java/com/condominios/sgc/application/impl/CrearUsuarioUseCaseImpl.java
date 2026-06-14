package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.dto.command.CrearUsuarioCommand;
import com.condominios.sgc.application.dto.response.UsuarioResponse;
import com.condominios.sgc.application.usecase.CrearUsuarioUseCase;
import com.condominios.sgc.domain.model.UsuarioModel;
import com.condominios.sgc.domain.port.UsuarioPort;

public class CrearUsuarioUseCaseImpl implements CrearUsuarioUseCase {
    private final UsuarioPort usuarioPort;

    public CrearUsuarioUseCaseImpl(UsuarioPort usuarioPort) {
        this.usuarioPort = usuarioPort;
    }

    @Override
    public UsuarioResponse ejecutar(CrearUsuarioCommand command) {
        UsuarioModel usuario = new UsuarioModel(
            command.nombres(), command.apellidos(), command.correo(),
            command.telefono(), command.rol(), command.contrasena(), command.idCondominio());
        usuario = usuarioPort.guardar(usuario);
        return UsuarioResponse.desdeModelo(usuario);
    }
}

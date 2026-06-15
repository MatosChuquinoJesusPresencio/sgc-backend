package com.condominios.sgc.application.impl.usuario;

import com.condominios.sgc.application.dto.command.ActualizarMiUsuarioCommand;
import com.condominios.sgc.application.dto.response.UsuarioResponse;
import com.condominios.sgc.application.usecase.usuario.ActualizarMiUsuarioUseCase;
import com.condominios.sgc.domain.exception.UsuarioException;
import com.condominios.sgc.domain.model.UsuarioModel;
import com.condominios.sgc.domain.port.UsuarioPort;

public class ActualizarMiUsuarioUseCaseImpl implements ActualizarMiUsuarioUseCase {
    private final UsuarioPort usuarioPort;

    public ActualizarMiUsuarioUseCaseImpl(UsuarioPort usuarioPort) {
        this.usuarioPort = usuarioPort;
    }

    @Override
    public UsuarioResponse ejecutar(Long id, ActualizarMiUsuarioCommand command) {
        UsuarioModel usuario = usuarioPort.obtenerPorId(id)
            .orElseThrow(UsuarioException::noEncontrado);

        usuario.actualizar(command.nombres(), command.apellidos(), command.telefono(),
            usuario.getRol(), usuario.getIdCondominio());

        usuario = usuarioPort.guardar(usuario);
        return UsuarioResponse.desdeModelo(usuario);
    }
}

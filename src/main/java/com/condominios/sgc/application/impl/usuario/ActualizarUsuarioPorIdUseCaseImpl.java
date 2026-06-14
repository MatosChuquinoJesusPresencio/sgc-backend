package com.condominios.sgc.application.impl.usuario;

import com.condominios.sgc.application.dto.command.ActualizarUsuarioCommand;
import com.condominios.sgc.application.dto.response.UsuarioResponse;
import com.condominios.sgc.application.usecase.usuario.ActualizarUsuarioPorIdUseCase;
import com.condominios.sgc.domain.exception.UsuarioException;
import com.condominios.sgc.domain.model.UsuarioModel;
import com.condominios.sgc.domain.port.UsuarioPort;

public class ActualizarUsuarioPorIdUseCaseImpl implements ActualizarUsuarioPorIdUseCase {
    private final UsuarioPort usuarioPort;

    public ActualizarUsuarioPorIdUseCaseImpl(UsuarioPort usuarioPort) {
        this.usuarioPort = usuarioPort;
    }

    @Override
    public UsuarioResponse ejecutar(Long id, ActualizarUsuarioCommand command) {
        UsuarioModel usuario = usuarioPort.obtenerPorId(id)
            .orElseThrow(UsuarioException::noEncontrado);

        usuario.actualizar(command.nombres(), command.apellidos(),
            command.telefono(), command.rol(), command.idCondominio());

        if (command.desasignarCondominio()) {
            usuario.desasignarCondominio();
        }

        usuario = usuarioPort.guardar(usuario);
        return UsuarioResponse.desdeModelo(usuario);
    }
}

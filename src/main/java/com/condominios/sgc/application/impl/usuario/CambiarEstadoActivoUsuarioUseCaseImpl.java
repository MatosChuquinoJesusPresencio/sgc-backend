package com.condominios.sgc.application.impl.usuario;

import com.condominios.sgc.application.dto.response.UsuarioResponse;
import com.condominios.sgc.application.usecase.usuario.CambiarEstadoActivoUsuarioUseCase;
import com.condominios.sgc.domain.exception.UsuarioException;
import com.condominios.sgc.domain.model.UsuarioModel;
import com.condominios.sgc.domain.port.UsuarioPort;

public class CambiarEstadoActivoUsuarioUseCaseImpl implements CambiarEstadoActivoUsuarioUseCase {
    private final UsuarioPort usuarioPort;

    public CambiarEstadoActivoUsuarioUseCaseImpl(UsuarioPort usuarioPort) {
        this.usuarioPort = usuarioPort;
    }

    @Override
    public UsuarioResponse ejecutar(Long id, boolean activo) {
        UsuarioModel usuario = usuarioPort.obtenerPorId(id)
            .orElseThrow(UsuarioException::noEncontrado);
        if (activo) {
            usuario.activar();
        } else {
            usuario.desactivar();
        }
        usuario = usuarioPort.guardar(usuario);
        return UsuarioResponse.desdeModelo(usuario);
    }
}

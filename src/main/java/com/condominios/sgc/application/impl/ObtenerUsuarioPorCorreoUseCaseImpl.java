package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.dto.response.UsuarioResponse;
import com.condominios.sgc.application.usecase.ObtenerUsuarioPorCorreoUseCase;
import com.condominios.sgc.domain.exception.UsuarioException;
import com.condominios.sgc.domain.port.UsuarioPort;

public class ObtenerUsuarioPorCorreoUseCaseImpl implements ObtenerUsuarioPorCorreoUseCase {
    private final UsuarioPort usuarioPort;

    public ObtenerUsuarioPorCorreoUseCaseImpl(UsuarioPort usuarioPort) {
        this.usuarioPort = usuarioPort;
    }

    @Override
    public UsuarioResponse ejecutar(String correo) {
        return usuarioPort.obtenerPorCorreo(correo)
            .map(UsuarioResponse::desdeModelo)
            .orElseThrow(UsuarioException::noEncontrado);
    }
}

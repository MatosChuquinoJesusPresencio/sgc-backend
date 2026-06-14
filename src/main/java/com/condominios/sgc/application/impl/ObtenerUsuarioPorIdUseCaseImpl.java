package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.dto.response.UsuarioResponse;
import com.condominios.sgc.application.usecase.ObtenerUsuarioPorIdUseCase;
import com.condominios.sgc.domain.exception.UsuarioException;
import com.condominios.sgc.domain.port.UsuarioPort;

public class ObtenerUsuarioPorIdUseCaseImpl implements ObtenerUsuarioPorIdUseCase {
    private final UsuarioPort usuarioPort;

    public ObtenerUsuarioPorIdUseCaseImpl(UsuarioPort usuarioPort) {
        this.usuarioPort = usuarioPort;
    }

    @Override
    public UsuarioResponse ejecutar(Long id) {
        return usuarioPort.obtenerPorId(id)
            .map(UsuarioResponse::desdeModelo)
            .orElseThrow(UsuarioException::noEncontrado);
    }
}

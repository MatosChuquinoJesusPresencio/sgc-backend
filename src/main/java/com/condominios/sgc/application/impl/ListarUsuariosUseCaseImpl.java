package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.usecase.ListarUsuariosUseCase;
import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.UsuarioModel;
import com.condominios.sgc.domain.port.UsuarioPort;

public class ListarUsuariosUseCaseImpl implements ListarUsuariosUseCase {

    private final UsuarioPort usuarioPort;

    public ListarUsuariosUseCaseImpl(UsuarioPort usuarioPort) {
        this.usuarioPort = usuarioPort;
    }

    @Override
    public PaginacionResponse<UsuarioModel> ejecutar(PaginacionRequest request) {
        return usuarioPort.findAll(request);
    }
}

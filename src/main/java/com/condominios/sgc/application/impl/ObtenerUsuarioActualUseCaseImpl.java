package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.dto.command.ObtenerUsuarioActualCommand;
import com.condominios.sgc.application.dto.result.UsuarioActualResult;
import com.condominios.sgc.application.port.in.ObtenerUsuarioActualUseCase;
import com.condominios.sgc.application.port.out.UsuarioRepositoryPort;
import com.condominios.sgc.domain.shared.exception.UsuarioException;

public class ObtenerUsuarioActualUseCaseImpl implements ObtenerUsuarioActualUseCase {

    private final UsuarioRepositoryPort usuarioRepo;

    public ObtenerUsuarioActualUseCaseImpl(UsuarioRepositoryPort usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }

    @Override
    public UsuarioActualResult ejecutar(ObtenerUsuarioActualCommand command) {
        var usuario = usuarioRepo.buscarPorId(command.idUsuario())
                .orElseThrow(UsuarioException::noEncontrado);
        return new UsuarioActualResult(
                usuario.getId(), usuario.getNombres(), usuario.getApellidos(),
                usuario.getRol(), usuario.getIdCondominio());
    }
}

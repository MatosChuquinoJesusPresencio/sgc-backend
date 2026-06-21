package com.condominios.sgc.application.service;

import com.condominios.sgc.application.dto.result.UsuarioActualResult;
import com.condominios.sgc.application.port.in.ObtenerUsuarioActualUseCase;
import com.condominios.sgc.application.port.out.UsuarioRepositoryPort;
import com.condominios.sgc.application.port.out.service.SecurityServicePort;
import com.condominios.sgc.domain.shared.exception.UsuarioException;

public class ObtenerUsuarioActualService implements ObtenerUsuarioActualUseCase {

    private final UsuarioRepositoryPort usuarioRepository;
    private final SecurityServicePort securityService;

    public ObtenerUsuarioActualService(UsuarioRepositoryPort usuarioRepository, SecurityServicePort securityService) {
        this.usuarioRepository = usuarioRepository;
        this.securityService = securityService;
    }

    @Override
    public UsuarioActualResult ejecutar() {
        Long id = securityService.obtenerIdUsuario();
        var usuario = usuarioRepository.buscarPorId(id)
            .orElseThrow(UsuarioException::noEncontrado);

        return new UsuarioActualResult(
            usuario.getId(),
            usuario.getNombreCompleto().nombres(),
            usuario.getNombreCompleto().apellidos(),
            usuario.getRol(),
            usuario.getIdCondominio());
    }
}

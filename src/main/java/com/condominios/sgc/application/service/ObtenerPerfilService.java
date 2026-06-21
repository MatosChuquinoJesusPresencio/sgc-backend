package com.condominios.sgc.application.service;

import com.condominios.sgc.application.dto.result.PerfilUsuarioResult;
import com.condominios.sgc.application.port.in.ObtenerPerfilUseCase;
import com.condominios.sgc.application.port.out.UsuarioRepositoryPort;
import com.condominios.sgc.application.port.out.service.SecurityServicePort;
import com.condominios.sgc.domain.shared.exception.UsuarioException;

public class ObtenerPerfilService implements ObtenerPerfilUseCase {

    private final SecurityServicePort securityService;
    private final UsuarioRepositoryPort usuarioRepository;

    public ObtenerPerfilService(SecurityServicePort securityService, UsuarioRepositoryPort usuarioRepository) {
        this.securityService = securityService;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public PerfilUsuarioResult ejecutar() {
        var id = securityService.obtenerIdUsuario();
        var usuario = usuarioRepository.buscarPorId(id)
            .orElseThrow(UsuarioException::noEncontrado);

        return new PerfilUsuarioResult(
            usuario.getId(),
            usuario.getCorreo().direccion(),
            usuario.getNombres(),
            usuario.getApellidos(),
            usuario.getTelefono() != null ? usuario.getTelefono().numero() : null,
            usuario.getRol(),
            usuario.getActivo(),
            usuario.getCorreoVerificado(),
            usuario.getIdCondominio(),
            usuario.getFechaCreacion());
    }
}

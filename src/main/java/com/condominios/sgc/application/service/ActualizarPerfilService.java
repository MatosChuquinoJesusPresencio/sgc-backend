package com.condominios.sgc.application.service;

import com.condominios.sgc.application.port.in.ActualizarPerfilUseCase;
import com.condominios.sgc.application.port.out.UsuarioRepositoryPort;
import com.condominios.sgc.application.port.out.service.SecurityServicePort;
import com.condominios.sgc.domain.shared.exception.UsuarioException;

public class ActualizarPerfilService implements ActualizarPerfilUseCase {

    private final SecurityServicePort securityService;
    private final UsuarioRepositoryPort usuarioRepository;

    public ActualizarPerfilService(SecurityServicePort securityService, UsuarioRepositoryPort usuarioRepository) {
        this.securityService = securityService;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void ejecutar(String nombres, String apellidos, String telefono) {
        var id = securityService.obtenerIdUsuario();
        var usuario = usuarioRepository.buscarPorId(id)
            .orElseThrow(UsuarioException::noEncontrado);

        usuario.actualizar(nombres, apellidos, telefono);
        usuarioRepository.guardar(usuario);
    }
}

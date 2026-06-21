package com.condominios.sgc.application.service;

import com.condominios.sgc.application.port.in.CambiarContrasenaUseCase;
import com.condominios.sgc.application.port.out.UsuarioRepositoryPort;
import com.condominios.sgc.application.port.out.service.HashServicePort;
import com.condominios.sgc.application.port.out.service.SecurityServicePort;
import com.condominios.sgc.domain.shared.exception.AutenticacionException;
import com.condominios.sgc.domain.shared.exception.UsuarioException;

public class CambiarContrasenaService implements CambiarContrasenaUseCase {

    private final SecurityServicePort securityService;
    private final UsuarioRepositoryPort usuarioRepository;
    private final HashServicePort hashService;

    public CambiarContrasenaService(SecurityServicePort securityService,
            UsuarioRepositoryPort usuarioRepository,
            HashServicePort hashService) {
        this.securityService = securityService;
        this.usuarioRepository = usuarioRepository;
        this.hashService = hashService;
    }

    @Override
    public void ejecutar(String contrasenaActual, String nuevaContrasena) {
        var id = securityService.obtenerIdUsuario();
        var usuario = usuarioRepository.buscarPorId(id)
            .orElseThrow(UsuarioException::noEncontrado);

        if (!hashService.verificar(contrasenaActual, usuario.getContrasena()))
            throw AutenticacionException.credencialesInvalidas();

        usuario.cambiarContrasena(hashService.hashear(nuevaContrasena));
        usuarioRepository.guardar(usuario);
    }
}

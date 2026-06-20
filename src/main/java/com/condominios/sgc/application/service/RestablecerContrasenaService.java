package com.condominios.sgc.application.service;

import com.condominios.sgc.application.port.in.RestablecerContrasenaUseCase;
import com.condominios.sgc.application.port.out.TokenRepositoryPort;
import com.condominios.sgc.application.port.out.UsuarioRepositoryPort;
import com.condominios.sgc.application.port.out.service.HashServicePort;
import com.condominios.sgc.domain.shared.exception.AutenticacionException;

public class RestablecerContrasenaService implements RestablecerContrasenaUseCase {

    private final TokenRepositoryPort tokenRepository;
    private final HashServicePort hashService;
    private final UsuarioRepositoryPort usuarioRepository;

    public RestablecerContrasenaService(TokenRepositoryPort tokenRepository,
                                         HashServicePort hashService,
                                         UsuarioRepositoryPort usuarioRepository) {
        this.tokenRepository = tokenRepository;
        this.hashService = hashService;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void ejecutar(String token, String contrasena) {
        var tokenModel = tokenRepository.obtenerPorToken(token)
            .orElseThrow(AutenticacionException::credencialesInvalidas);

        tokenModel.validarExpiracion();

        var usuario = usuarioRepository.buscarPorId(tokenModel.getIdUsuario())
            .orElseThrow(AutenticacionException::credencialesInvalidas);

        usuario.cambiarContrasena(hashService.hashear(contrasena));
        usuarioRepository.guardar(usuario);
        tokenRepository.eliminar(tokenModel);
    }
}

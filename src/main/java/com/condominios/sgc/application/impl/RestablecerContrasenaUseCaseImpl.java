package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.dto.command.RestablecerContrasenaCommand;
import com.condominios.sgc.application.port.in.RestablecerContrasenaUseCase;
import com.condominios.sgc.application.port.out.TokenRepositoryPort;
import com.condominios.sgc.application.port.out.UsuarioRepositoryPort;
import com.condominios.sgc.application.port.out.service.HashServicePort;
import com.condominios.sgc.application.port.out.service.JwtServicePort;
import com.condominios.sgc.domain.shared.exception.TokenException;

public class RestablecerContrasenaUseCaseImpl implements RestablecerContrasenaUseCase {

    private final TokenRepositoryPort tokenRepo;
    private final JwtServicePort jwtService;
    private final HashServicePort hashService;
    private final UsuarioRepositoryPort usuarioRepo;

    public RestablecerContrasenaUseCaseImpl(
            TokenRepositoryPort tokenRepo,
            JwtServicePort jwtService,
            HashServicePort hashService,
            UsuarioRepositoryPort usuarioRepo) {
        this.tokenRepo = tokenRepo;
        this.jwtService = jwtService;
        this.hashService = hashService;
        this.usuarioRepo = usuarioRepo;
    }

    @Override
    public void ejecutar(RestablecerContrasenaCommand command) {
        var token = tokenRepo.buscarPorToken(command.tokenRestablecimiento())
                .orElseThrow(TokenException::noEncontrado);

        token.usar();
        tokenRepo.guardar(token);

        var usuarioId = jwtService.obtenerUsuarioId(command.tokenRestablecimiento());
        var usuario = usuarioRepo.buscarPorId(usuarioId)
                .orElseThrow(TokenException::noEncontrado);

        var hash = hashService.hashear(command.nuevaContrasena());
        usuario.cambiarContrasena(hash);
        usuarioRepo.guardar(usuario);
    }
}

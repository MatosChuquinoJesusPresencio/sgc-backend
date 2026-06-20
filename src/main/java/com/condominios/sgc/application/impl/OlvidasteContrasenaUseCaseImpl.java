package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.dto.command.OlvidasteContrasenaCommand;
import com.condominios.sgc.application.port.in.OlvidasteContrasenaUseCase;
import com.condominios.sgc.application.port.out.TokenRepositoryPort;
import com.condominios.sgc.application.port.out.UsuarioRepositoryPort;
import com.condominios.sgc.application.port.out.service.CorreoServicePort;
import com.condominios.sgc.application.port.out.service.JwtServicePort;
import com.condominios.sgc.domain.model.TokenModel;
import com.condominios.sgc.domain.type.TipoToken;

public class OlvidasteContrasenaUseCaseImpl implements OlvidasteContrasenaUseCase {

    private final UsuarioRepositoryPort usuarioRepo;
    private final JwtServicePort jwtService;
    private final TokenRepositoryPort tokenRepo;
    private final CorreoServicePort correoService;

    public OlvidasteContrasenaUseCaseImpl(
            UsuarioRepositoryPort usuarioRepo,
            JwtServicePort jwtService,
            TokenRepositoryPort tokenRepo,
            CorreoServicePort correoService) {
        this.usuarioRepo = usuarioRepo;
        this.jwtService = jwtService;
        this.tokenRepo = tokenRepo;
        this.correoService = correoService;
    }

    @Override
    public void ejecutar(OlvidasteContrasenaCommand command) {
        usuarioRepo.buscarPorCorreo(command.correo()).ifPresent(usuario -> {
            var resetToken = jwtService.generarTokenRestablecimiento(usuario.getId());
            var expiracion = jwtService.obtenerExpiracion(resetToken);

            tokenRepo.guardar(new TokenModel(
                    TipoToken.REESTABLECIMIENTO, resetToken, expiracion, usuario.getId()));

            correoService.enviarResetContrasena(usuario.getCorreo().direccion(), resetToken);
        });
    }
}

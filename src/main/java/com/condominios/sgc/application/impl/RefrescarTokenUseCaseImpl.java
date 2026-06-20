package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.dto.command.RefrescarTokenCommand;
import com.condominios.sgc.application.dto.result.SesionUsuarioResult;
import com.condominios.sgc.application.dto.result.UsuarioActualResult;
import com.condominios.sgc.application.port.in.RefrescarTokenUseCase;
import com.condominios.sgc.application.port.out.TokenRepositoryPort;
import com.condominios.sgc.application.port.out.UsuarioRepositoryPort;
import com.condominios.sgc.application.port.out.service.JwtServicePort;
import com.condominios.sgc.domain.model.TokenModel;
import com.condominios.sgc.domain.shared.exception.TokenException;
import com.condominios.sgc.domain.type.TipoToken;

public class RefrescarTokenUseCaseImpl implements RefrescarTokenUseCase {

    private final TokenRepositoryPort tokenRepo;
    private final JwtServicePort jwtService;
    private final UsuarioRepositoryPort usuarioRepo;

    public RefrescarTokenUseCaseImpl(
            TokenRepositoryPort tokenRepo,
            JwtServicePort jwtService,
            UsuarioRepositoryPort usuarioRepo) {
        this.tokenRepo = tokenRepo;
        this.jwtService = jwtService;
        this.usuarioRepo = usuarioRepo;
    }

    @Override
    public SesionUsuarioResult ejecutar(RefrescarTokenCommand command) {
        var token = tokenRepo.buscarPorToken(command.tokenRefresco())
                .orElseThrow(TokenException::noEncontrado);

        token.usar();
        tokenRepo.guardar(token);

        var usuarioId = jwtService.obtenerUsuarioId(command.tokenRefresco());
        var usuario = usuarioRepo.buscarPorId(usuarioId)
                .orElseThrow(TokenException::noEncontrado);

        var nuevoAccessToken = jwtService.generarTokenAcceso(
                usuario.getId(), usuario.getCorreo().direccion(), usuario.getRol());
        var oldClaims = jwtService.validarToken(command.tokenRefresco());
        var recuerdame = oldClaims.get("recuerdame", Boolean.class);
        var nuevoRefreshToken = jwtService.generarTokenRefresco(usuario.getId(), recuerdame != null && recuerdame);
        var expiracion = jwtService.obtenerExpiracion(nuevoRefreshToken);

        tokenRepo.guardar(new TokenModel(TipoToken.REFRESCO, nuevoRefreshToken, expiracion, usuario.getId()));

        var usuarioActual = new UsuarioActualResult(
                usuario.getId(), usuario.getNombres(), usuario.getApellidos(),
                usuario.getRol(), usuario.getIdCondominio());

        return new SesionUsuarioResult(usuarioActual, nuevoAccessToken, nuevoRefreshToken);
    }
}

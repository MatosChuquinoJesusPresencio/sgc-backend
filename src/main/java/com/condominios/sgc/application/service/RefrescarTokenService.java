package com.condominios.sgc.application.service;

import com.condominios.sgc.application.dto.result.SesionUsuarioResult;
import com.condominios.sgc.application.dto.result.UsuarioActualResult;
import com.condominios.sgc.application.port.in.RefrescarTokenUseCase;
import com.condominios.sgc.application.port.out.TokenRepositoryPort;
import com.condominios.sgc.application.port.out.UsuarioRepositoryPort;
import com.condominios.sgc.application.port.out.service.JwtServicePort;
import com.condominios.sgc.domain.model.TokenModel;
import com.condominios.sgc.domain.shared.exception.AutenticacionException;
import com.condominios.sgc.domain.type.TipoToken;

public class RefrescarTokenService implements RefrescarTokenUseCase {

    private final TokenRepositoryPort tokenRepository;
    private final JwtServicePort jwtService;
    private final UsuarioRepositoryPort usuarioRepository;

    public RefrescarTokenService(TokenRepositoryPort tokenRepository,
                                  JwtServicePort jwtService,
                                  UsuarioRepositoryPort usuarioRepository) {
        this.tokenRepository = tokenRepository;
        this.jwtService = jwtService;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public SesionUsuarioResult ejecutar(String tokenRefresco) {
        if (!jwtService.validar(tokenRefresco)) {
            throw AutenticacionException.credencialesInvalidas();
        }

        var tokenModel = tokenRepository.obtenerPorToken(tokenRefresco)
            .orElseThrow(AutenticacionException::credencialesInvalidas);

        tokenModel.validarExpiracion();
        tokenRepository.eliminar(tokenModel);

        var usuario = usuarioRepository.buscarPorId(tokenModel.getIdUsuario())
            .orElseThrow(AutenticacionException::credencialesInvalidas);

        var valorTokenAcceso = jwtService.generarTokenAcceso(usuario.getId(), usuario.getCorreo().direccion(), usuario.getRol());
        var valorTokenRefresco = jwtService.generarTokenRefresco(usuario.getId(), false);

        tokenRepository.guardar(new TokenModel(TipoToken.REFRESCO, valorTokenRefresco,
            jwtService.obtenerExpiracion(TipoToken.REFRESCO), usuario.getId()));

        var usuarioActual = new UsuarioActualResult(
            usuario.getId(),
            usuario.getNombreCompleto().nombres(),
            usuario.getNombreCompleto().apellidos(),
            usuario.getCorreo().direccion(),
            usuario.getRol(),
            usuario.getIdCondominio());

        long expAccesoMs = jwtService.obtenerDuracionMs(TipoToken.ACCESO, false);
        long expRefrescoMs = jwtService.obtenerDuracionMs(TipoToken.REFRESCO, false);

        return new SesionUsuarioResult(valorTokenAcceso, valorTokenRefresco, usuarioActual, expAccesoMs, expRefrescoMs);
    }
}

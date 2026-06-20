package com.condominios.sgc.application.service;

import com.condominios.sgc.application.port.in.OlvidasteContrasenaUseCase;
import com.condominios.sgc.application.port.out.TokenRepositoryPort;
import com.condominios.sgc.application.port.out.UsuarioRepositoryPort;
import com.condominios.sgc.application.port.out.service.CorreoServicePort;
import com.condominios.sgc.application.port.out.service.JwtServicePort;
import com.condominios.sgc.domain.model.TokenModel;
import com.condominios.sgc.domain.shared.exception.AutenticacionException;
import com.condominios.sgc.domain.type.TipoToken;

public class OlvidasteContrasenaService implements OlvidasteContrasenaUseCase {

    private final UsuarioRepositoryPort usuarioRepository;
    private final JwtServicePort jwtService;
    private final TokenRepositoryPort tokenRepository;
    private final CorreoServicePort correoService;

    public OlvidasteContrasenaService(UsuarioRepositoryPort usuarioRepository,
                                      JwtServicePort jwtService,
                                      TokenRepositoryPort tokenRepository,
                                      CorreoServicePort correoService) {
        this.usuarioRepository = usuarioRepository;
        this.jwtService = jwtService;
        this.tokenRepository = tokenRepository;
        this.correoService = correoService;
    }

    @Override
    public void ejecutar(String correo) {
        var usuario = usuarioRepository.buscarPorCorreo(correo)
            .orElseThrow(AutenticacionException::credencialesInvalidas);

        var valorToken = jwtService.generarToken(usuario.getId(), TipoToken.REESTABLECIMIENTO);
        var expiracion = jwtService.obtenerExpiracion(TipoToken.REESTABLECIMIENTO);

        tokenRepository.guardar(new TokenModel(TipoToken.REESTABLECIMIENTO, valorToken, expiracion, usuario.getId()));

        var nombreCompleto = usuario.getNombreCompleto().nombres() + " " + usuario.getNombreCompleto().apellidos();
        correoService.enviarRestablecimiento(usuario.getCorreo().direccion(), nombreCompleto, valorToken);
    }
}

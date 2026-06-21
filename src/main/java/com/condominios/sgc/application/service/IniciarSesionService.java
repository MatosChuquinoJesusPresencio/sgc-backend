package com.condominios.sgc.application.service;

import com.condominios.sgc.application.dto.result.SesionUsuarioResult;
import com.condominios.sgc.application.dto.result.UsuarioActualResult;
import com.condominios.sgc.application.port.in.IniciarSesionUseCase;
import com.condominios.sgc.application.port.out.TokenRepositoryPort;
import com.condominios.sgc.application.port.out.UsuarioRepositoryPort;
import com.condominios.sgc.application.port.out.service.HashServicePort;
import com.condominios.sgc.application.port.out.service.JwtServicePort;
import com.condominios.sgc.domain.model.TokenModel;
import com.condominios.sgc.domain.shared.exception.AutenticacionException;
import com.condominios.sgc.domain.shared.exception.UsuarioException;
import com.condominios.sgc.domain.type.TipoToken;

public class IniciarSesionService implements IniciarSesionUseCase {
    private final UsuarioRepositoryPort usuarioRepository;
    private final HashServicePort hashService;
    private final JwtServicePort jwtService;
    private final TokenRepositoryPort tokenRepository;

    public IniciarSesionService(UsuarioRepositoryPort usuarioRepository, HashServicePort hashService,
            JwtServicePort jwtService, TokenRepositoryPort tokenRepository) {
        this.usuarioRepository = usuarioRepository;
        this.hashService = hashService;
        this.jwtService = jwtService;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public SesionUsuarioResult ejecutar(String correo, String contrasena, Boolean recuerdame) {
        var usuario = usuarioRepository.buscarPorCorreo(correo)
                .orElseThrow(AutenticacionException::credencialesInvalidas);

        if (!hashService.verificar(contrasena, usuario.getContrasena()))
            throw AutenticacionException.credencialesInvalidas();

        if (!usuario.getActivo())
            throw UsuarioException.usuarioInactivo();

        var esRecuerdame = recuerdame != null && recuerdame;

        var valorTokenAcceso = jwtService.generarTokenAcceso(usuario.getId(), usuario.getCorreo().direccion(),
                usuario.getRol());
        var valorTokenRefresco = jwtService.generarTokenRefresco(usuario.getId(), esRecuerdame);

        tokenRepository.guardar(new TokenModel(TipoToken.REFRESCO, valorTokenRefresco,
                jwtService.obtenerExpiracion(TipoToken.REFRESCO), usuario.getId()));

        var usuarioActual = new UsuarioActualResult(usuario.getId(),
                usuario.getNombreCompleto().nombres(),
                usuario.getNombreCompleto().apellidos(),
                usuario.getRol(),
                usuario.getIdCondominio());

        long expAccesoMs = jwtService.obtenerDuracionMs(TipoToken.ACCESO, esRecuerdame);
        long expRefrescoMs = jwtService.obtenerDuracionMs(TipoToken.REFRESCO, esRecuerdame);

        return new SesionUsuarioResult(
                valorTokenAcceso,
                valorTokenRefresco,
                usuarioActual,
                expAccesoMs,
                expRefrescoMs);
    }
}

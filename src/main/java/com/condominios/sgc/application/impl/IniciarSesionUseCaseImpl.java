package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.dto.command.IniciarSesionCommand;
import com.condominios.sgc.application.dto.result.SesionUsuarioResult;
import com.condominios.sgc.application.dto.result.UsuarioActualResult;
import com.condominios.sgc.application.port.in.IniciarSesionUseCase;
import com.condominios.sgc.application.port.out.TokenRepositoryPort;
import com.condominios.sgc.application.port.out.UsuarioRepositoryPort;
import com.condominios.sgc.application.port.out.service.HashServicePort;
import com.condominios.sgc.application.port.out.service.JwtServicePort;
import com.condominios.sgc.domain.model.TokenModel;
import com.condominios.sgc.domain.shared.exception.AutenticacionException;
import com.condominios.sgc.domain.type.TipoToken;

public class IniciarSesionUseCaseImpl implements IniciarSesionUseCase {

    private final UsuarioRepositoryPort usuarioRepo;
    private final JwtServicePort jwtService;
    private final HashServicePort hashService;
    private final TokenRepositoryPort tokenRepo;

    public IniciarSesionUseCaseImpl(
            UsuarioRepositoryPort usuarioRepo,
            JwtServicePort jwtService,
            HashServicePort hashService,
            TokenRepositoryPort tokenRepo) {
        this.usuarioRepo = usuarioRepo;
        this.jwtService = jwtService;
        this.hashService = hashService;
        this.tokenRepo = tokenRepo;
    }

    @Override
    public SesionUsuarioResult ejecutar(IniciarSesionCommand command) {
        var usuario = usuarioRepo.buscarPorCorreo(command.correo())
                .orElseThrow(AutenticacionException::credencialesInvalidas);

        if (!usuario.getActivo())
            throw AutenticacionException.cuentaDesactivada();

        if (!hashService.verificar(command.contrasena(), usuario.getContrasena()))
            throw AutenticacionException.credencialesInvalidas();

        var tokenAcceso = jwtService.generarTokenAcceso(
                usuario.getId(), usuario.getCorreo().direccion(), usuario.getRol());
        var recuerdame = command.recuerdame() != null && command.recuerdame();
        var tokenRefresco = jwtService.generarTokenRefresco(usuario.getId(), recuerdame);
        var expiracion = jwtService.obtenerExpiracion(tokenRefresco);

        tokenRepo.guardar(new TokenModel(TipoToken.REFRESCO, tokenRefresco, expiracion, usuario.getId()));

        var usuarioActual = new UsuarioActualResult(
                usuario.getId(), usuario.getNombres(), usuario.getApellidos(),
                usuario.getRol(), usuario.getIdCondominio());

        return new SesionUsuarioResult(usuarioActual, tokenAcceso, tokenRefresco);
    }
}

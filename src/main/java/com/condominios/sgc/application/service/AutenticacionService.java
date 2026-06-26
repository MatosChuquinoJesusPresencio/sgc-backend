package com.condominios.sgc.application.service;

import com.condominios.sgc.application.dto.result.SesionUsuarioResult;
import com.condominios.sgc.application.dto.result.UsuarioActualResult;
import com.condominios.sgc.application.port.in.ActualizarCorreoUseCase;
import com.condominios.sgc.application.port.in.CambiarContrasenaUseCase;
import com.condominios.sgc.application.port.in.CerrarSesionUseCase;
import com.condominios.sgc.application.port.in.IniciarSesionUseCase;
import com.condominios.sgc.application.port.in.ObtenerUsuarioActualUseCase;
import com.condominios.sgc.application.port.in.OlvidasteContrasenaUseCase;
import com.condominios.sgc.application.port.in.RefrescarTokenUseCase;
import com.condominios.sgc.application.port.in.RestablecerContrasenaUseCase;
import com.condominios.sgc.application.port.in.VerificarEmailUseCase;
import com.condominios.sgc.application.port.out.TokenRepositoryPort;
import com.condominios.sgc.application.port.out.UsuarioRepositoryPort;
import com.condominios.sgc.application.port.out.service.CorreoServicePort;
import com.condominios.sgc.application.port.out.service.HashServicePort;
import com.condominios.sgc.application.port.out.service.JwtServicePort;
import com.condominios.sgc.application.port.out.service.SecurityServicePort;
import com.condominios.sgc.domain.model.TokenModel;
import com.condominios.sgc.domain.shared.exception.AutenticacionException;
import com.condominios.sgc.domain.shared.exception.TokenException;
import com.condominios.sgc.domain.shared.exception.UsuarioException;
import com.condominios.sgc.domain.type.TipoToken;

import org.springframework.transaction.annotation.Transactional;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;

@Transactional(readOnly = true)
public class AutenticacionService implements IniciarSesionUseCase, RefrescarTokenUseCase,
        CerrarSesionUseCase, OlvidasteContrasenaUseCase, RestablecerContrasenaUseCase,
        ObtenerUsuarioActualUseCase, CambiarContrasenaUseCase, ActualizarCorreoUseCase,
        VerificarEmailUseCase {

    private final UsuarioRepositoryPort usuarioRepository;
    private final HashServicePort hashService;
    private final JwtServicePort jwtService;
    private final TokenRepositoryPort tokenRepository;
    private final SecurityServicePort securityService;
    private final CorreoServicePort correoService;

    public AutenticacionService(UsuarioRepositoryPort usuarioRepository,
                                HashServicePort hashService,
                                JwtServicePort jwtService,
                                TokenRepositoryPort tokenRepository,
                                SecurityServicePort securityService,
                                CorreoServicePort correoService) {
        this.usuarioRepository = usuarioRepository;
        this.hashService = hashService;
        this.jwtService = jwtService;
        this.tokenRepository = tokenRepository;
        this.securityService = securityService;
        this.correoService = correoService;
    }

    @Override
    @Transactional
    @RateLimiter(name = "login-password")
    public SesionUsuarioResult iniciarSesion(String correo, String contrasena, Boolean recuerdame) {
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
                usuario.getCorreo().direccion(),
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

    @Override
    @Transactional
    public SesionUsuarioResult refrescarToken(String tokenRefresco) {
        if (!jwtService.validar(tokenRefresco))
            throw AutenticacionException.credencialesInvalidas();

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

    @Override
    @Transactional
    public void cerrarSesion(String tokenRefresco) {
        tokenRepository.obtenerPorToken(tokenRefresco)
            .ifPresent(tokenRepository::eliminar);
    }

    @Override
    @Transactional
    @RateLimiter(name = "forgot-password")
    public void olvidasteContrasena(String correo) {
        var usuario = usuarioRepository.buscarPorCorreo(correo)
            .orElseThrow(AutenticacionException::credencialesInvalidas);

        var valorToken = jwtService.generarToken(usuario.getId(), TipoToken.REESTABLECIMIENTO);
        var expiracion = jwtService.obtenerExpiracion(TipoToken.REESTABLECIMIENTO);

        tokenRepository.guardar(new TokenModel(TipoToken.REESTABLECIMIENTO, valorToken, expiracion, usuario.getId()));

        var nombreCompleto = usuario.getNombreCompleto().nombres() + " " + usuario.getNombreCompleto().apellidos();
        correoService.enviarRestablecimiento(usuario.getCorreo().direccion(), nombreCompleto, valorToken);
    }

    @Override
    @Transactional
    public void restablecerContrasena(String token, String contrasena) {
        var tokenModel = tokenRepository.obtenerPorToken(token)
            .orElseThrow(AutenticacionException::credencialesInvalidas);

        tokenModel.validarExpiracion();

        var usuario = usuarioRepository.buscarPorId(tokenModel.getIdUsuario())
            .orElseThrow(AutenticacionException::credencialesInvalidas);

        usuario.cambiarContrasena(hashService.hashear(contrasena));
        usuarioRepository.guardar(usuario);
        tokenRepository.eliminar(tokenModel);
    }

    @Override
    public UsuarioActualResult obtenerUsuarioActual() {
        Long id = securityService.obtenerIdUsuario();
        var usuario = usuarioRepository.buscarPorId(id)
            .orElseThrow(UsuarioException::noEncontrado);

        return new UsuarioActualResult(
            usuario.getId(),
            usuario.getNombreCompleto().nombres(),
            usuario.getNombreCompleto().apellidos(),
            usuario.getCorreo().direccion(),
            usuario.getRol(),
            usuario.getIdCondominio());
    }

    @Override
    @Transactional
    public void cambiarContrasena(String contrasenaActual, String nuevaContrasena) {
        var id = securityService.obtenerIdUsuario();
        var usuario = usuarioRepository.buscarPorId(id)
            .orElseThrow(UsuarioException::noEncontrado);

        if (!hashService.verificar(contrasenaActual, usuario.getContrasena()))
            throw AutenticacionException.credencialesInvalidas();

        usuario.cambiarContrasena(hashService.hashear(nuevaContrasena));
        usuarioRepository.guardar(usuario);
    }

    @Override
    @Transactional
    public void actualizarCorreo(String nuevoCorreo, String contrasena) {
        var idUsuario = securityService.obtenerIdUsuario();
        var usuario = usuarioRepository.buscarPorId(idUsuario)
            .orElseThrow(UsuarioException::noEncontrado);

        if (!hashService.verificar(contrasena, usuario.getContrasena()))
            throw AutenticacionException.credencialesInvalidas();

        usuarioRepository.buscarPorCorreo(nuevoCorreo)
            .ifPresent(u -> { throw UsuarioException.correoYaRegistrado(); });

        usuario.cambiarCorreo(nuevoCorreo);

        var valorToken = jwtService.generarToken(idUsuario, TipoToken.VERIFICACION);
        tokenRepository.guardar(new TokenModel(TipoToken.VERIFICACION, valorToken,
            jwtService.obtenerExpiracion(TipoToken.VERIFICACION), idUsuario));

        correoService.enviarVerificacion(nuevoCorreo,
            usuario.getNombreCompleto().formatoCompleto(), valorToken);

        usuarioRepository.guardar(usuario);
    }

    @Override
    @Transactional
    public void verificarEmail(String token) {
        var tokenModel = tokenRepository.obtenerPorToken(token)
            .orElseThrow(TokenException::noEncontrado);

        if (tokenModel.getTipo() != TipoToken.VERIFICACION)
            throw TokenException.tokenExpirado();

        tokenModel.validarExpiracion();

        var usuario = usuarioRepository.buscarPorId(tokenModel.getIdUsuario())
            .orElseThrow(UsuarioException::noEncontrado);

        usuario.confirmarCorreo();
        usuarioRepository.guardar(usuario);
        tokenRepository.eliminar(tokenModel);
    }
}

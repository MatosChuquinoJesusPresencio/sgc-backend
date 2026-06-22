package com.condominios.sgc.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.condominios.sgc.application.port.in.ActualizarCorreoUseCase;
import com.condominios.sgc.application.port.in.ActualizarPerfilUseCase;
import com.condominios.sgc.application.port.in.CambiarContrasenaUseCase;
import com.condominios.sgc.application.port.in.CerrarSesionUseCase;
import com.condominios.sgc.application.port.in.IniciarSesionUseCase;
import com.condominios.sgc.application.port.in.ObtenerPerfilUseCase;
import com.condominios.sgc.application.port.in.ObtenerUsuarioActualUseCase;
import com.condominios.sgc.application.port.in.OlvidasteContrasenaUseCase;
import com.condominios.sgc.application.port.in.RefrescarTokenUseCase;
import com.condominios.sgc.application.port.in.RestablecerContrasenaUseCase;
import com.condominios.sgc.application.port.in.VerificarEmailUseCase;
import com.condominios.sgc.application.port.out.CondominioRepositoryPort;
import com.condominios.sgc.application.port.out.TokenRepositoryPort;
import com.condominios.sgc.application.port.out.UsuarioRepositoryPort;
import com.condominios.sgc.application.port.out.service.CorreoServicePort;
import com.condominios.sgc.application.port.out.service.HashServicePort;
import com.condominios.sgc.application.port.out.service.JwtServicePort;
import com.condominios.sgc.application.port.out.service.SecurityServicePort;
import com.condominios.sgc.application.service.AutenticacionService;
import com.condominios.sgc.application.service.GestionarAdministradorService;
import com.condominios.sgc.application.service.PerfilService;

@Configuration
public class AutenticacionConfig {

    @Bean
    public IniciarSesionUseCase iniciarSesionUseCase(
            UsuarioRepositoryPort usuarioRepository,
            HashServicePort hashService,
            JwtServicePort jwtService,
            TokenRepositoryPort tokenRepository,
            SecurityServicePort securityService,
            CorreoServicePort correoService) {
        var svc = new AutenticacionService(usuarioRepository, hashService, jwtService,
            tokenRepository, securityService, correoService);
        return svc::iniciarSesion;
    }

    @Bean
    public RefrescarTokenUseCase refrescarTokenUseCase(
            UsuarioRepositoryPort usuarioRepository,
            HashServicePort hashService,
            JwtServicePort jwtService,
            TokenRepositoryPort tokenRepository,
            SecurityServicePort securityService,
            CorreoServicePort correoService) {
        var svc = new AutenticacionService(usuarioRepository, hashService, jwtService,
            tokenRepository, securityService, correoService);
        return svc::refrescarToken;
    }

    @Bean
    public CerrarSesionUseCase cerrarSesionUseCase(
            UsuarioRepositoryPort usuarioRepository,
            HashServicePort hashService,
            JwtServicePort jwtService,
            TokenRepositoryPort tokenRepository,
            SecurityServicePort securityService,
            CorreoServicePort correoService) {
        var svc = new AutenticacionService(usuarioRepository, hashService, jwtService,
            tokenRepository, securityService, correoService);
        return svc::cerrarSesion;
    }

    @Bean
    public OlvidasteContrasenaUseCase olvidasteContrasenaUseCase(
            UsuarioRepositoryPort usuarioRepository,
            HashServicePort hashService,
            JwtServicePort jwtService,
            TokenRepositoryPort tokenRepository,
            SecurityServicePort securityService,
            CorreoServicePort correoService) {
        var svc = new AutenticacionService(usuarioRepository, hashService, jwtService,
            tokenRepository, securityService, correoService);
        return svc::olvidasteContrasena;
    }

    @Bean
    public RestablecerContrasenaUseCase restablecerContrasenaUseCase(
            UsuarioRepositoryPort usuarioRepository,
            HashServicePort hashService,
            JwtServicePort jwtService,
            TokenRepositoryPort tokenRepository,
            SecurityServicePort securityService,
            CorreoServicePort correoService) {
        var svc = new AutenticacionService(usuarioRepository, hashService, jwtService,
            tokenRepository, securityService, correoService);
        return svc::restablecerContrasena;
    }

    @Bean
    public ObtenerUsuarioActualUseCase obtenerUsuarioActualUseCase(
            UsuarioRepositoryPort usuarioRepository,
            HashServicePort hashService,
            JwtServicePort jwtService,
            TokenRepositoryPort tokenRepository,
            SecurityServicePort securityService,
            CorreoServicePort correoService) {
        var svc = new AutenticacionService(usuarioRepository, hashService, jwtService,
            tokenRepository, securityService, correoService);
        return svc::obtenerUsuarioActual;
    }

    @Bean
    public CambiarContrasenaUseCase cambiarContrasenaUseCase(
            UsuarioRepositoryPort usuarioRepository,
            HashServicePort hashService,
            JwtServicePort jwtService,
            TokenRepositoryPort tokenRepository,
            SecurityServicePort securityService,
            CorreoServicePort correoService) {
        var svc = new AutenticacionService(usuarioRepository, hashService, jwtService,
            tokenRepository, securityService, correoService);
        return svc::cambiarContrasena;
    }

    @Bean
    public ActualizarCorreoUseCase actualizarCorreoUseCase(
            UsuarioRepositoryPort usuarioRepository,
            HashServicePort hashService,
            JwtServicePort jwtService,
            TokenRepositoryPort tokenRepository,
            SecurityServicePort securityService,
            CorreoServicePort correoService) {
        var svc = new AutenticacionService(usuarioRepository, hashService, jwtService,
            tokenRepository, securityService, correoService);
        return svc::actualizarCorreo;
    }

    @Bean
    public VerificarEmailUseCase verificarEmailUseCase(
            UsuarioRepositoryPort usuarioRepository,
            HashServicePort hashService,
            JwtServicePort jwtService,
            TokenRepositoryPort tokenRepository,
            SecurityServicePort securityService,
            CorreoServicePort correoService) {
        var svc = new AutenticacionService(usuarioRepository, hashService, jwtService,
            tokenRepository, securityService, correoService);
        return svc::verificarEmail;
    }

    @Bean
    public ObtenerPerfilUseCase obtenerPerfilUseCase(
            SecurityServicePort securityService,
            UsuarioRepositoryPort usuarioRepository) {
        var svc = new PerfilService(securityService, usuarioRepository);
        return svc::obtenerPerfil;
    }

    @Bean
    public ActualizarPerfilUseCase actualizarPerfilUseCase(
            SecurityServicePort securityService,
            UsuarioRepositoryPort usuarioRepository) {
        var svc = new PerfilService(securityService, usuarioRepository);
        return svc::actualizarPerfil;
    }

    @Bean
    public GestionarAdministradorService gestionarAdministradorService(
            SecurityServicePort securityService,
            UsuarioRepositoryPort usuarioRepository,
            CondominioRepositoryPort condominioRepository,
            HashServicePort hashService,
            CorreoServicePort correoService) {
        return new GestionarAdministradorService(
            securityService, usuarioRepository, condominioRepository,
            hashService, correoService);
    }
}

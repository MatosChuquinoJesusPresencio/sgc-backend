package com.condominios.sgc.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.condominios.sgc.application.port.out.TokenRepositoryPort;
import com.condominios.sgc.application.port.out.UsuarioRepositoryPort;
import com.condominios.sgc.application.port.out.service.CorreoServicePort;
import com.condominios.sgc.application.port.out.service.HashServicePort;
import com.condominios.sgc.application.port.out.service.JwtServicePort;
import com.condominios.sgc.application.port.out.service.SecurityServicePort;
import com.condominios.sgc.application.service.CerrarSesionService;
import com.condominios.sgc.application.service.IniciarSesionService;
import com.condominios.sgc.application.service.ObtenerUsuarioActualService;
import com.condominios.sgc.application.service.OlvidasteContrasenaService;
import com.condominios.sgc.application.service.RefrescarTokenService;
import com.condominios.sgc.application.service.RestablecerContrasenaService;

@Configuration
public class AutenticacionConfig {

    @Bean
    public IniciarSesionService iniciarSesionService(
            UsuarioRepositoryPort usuarioRepository,
            HashServicePort hashService,
            JwtServicePort jwtService,
            TokenRepositoryPort tokenRepository) {
        return new IniciarSesionService(usuarioRepository, hashService, jwtService, tokenRepository);
    }

    @Bean
    public RefrescarTokenService refrescarTokenService(
            TokenRepositoryPort tokenRepository,
            JwtServicePort jwtService,
            UsuarioRepositoryPort usuarioRepository) {
        return new RefrescarTokenService(tokenRepository, jwtService, usuarioRepository);
    }

    @Bean
    public OlvidasteContrasenaService olvidasteContrasenaService(
            UsuarioRepositoryPort usuarioRepository,
            JwtServicePort jwtService,
            TokenRepositoryPort tokenRepository,
            CorreoServicePort correoService) {
        return new OlvidasteContrasenaService(usuarioRepository, jwtService, tokenRepository, correoService);
    }

    @Bean
    public RestablecerContrasenaService restablecerContrasenaService(
            TokenRepositoryPort tokenRepository,
            HashServicePort hashService,
            UsuarioRepositoryPort usuarioRepository) {
        return new RestablecerContrasenaService(tokenRepository, hashService, usuarioRepository);
    }

    @Bean
    public CerrarSesionService cerrarSesionService(
            TokenRepositoryPort tokenRepository) {
        return new CerrarSesionService(tokenRepository);
    }

    @Bean
    public ObtenerUsuarioActualService obtenerUsuarioActualService(
            UsuarioRepositoryPort usuarioRepository,
            SecurityServicePort securityService) {
        return new ObtenerUsuarioActualService(usuarioRepository, securityService);
    }
}

package com.condominios.sgc.infrastructure.config;

import com.condominios.sgc.application.impl.CerrarSesionUseCaseImpl;
import com.condominios.sgc.application.impl.IniciarSesionUseCaseImpl;
import com.condominios.sgc.application.impl.ObtenerUsuarioActualUseCaseImpl;
import com.condominios.sgc.application.impl.OlvidasteContrasenaUseCaseImpl;
import com.condominios.sgc.application.impl.RefrescarTokenUseCaseImpl;
import com.condominios.sgc.application.impl.RestablecerContrasenaUseCaseImpl;
import com.condominios.sgc.application.port.in.CerrarSesionUseCase;
import com.condominios.sgc.application.port.in.IniciarSesionUseCase;
import com.condominios.sgc.application.port.in.ObtenerUsuarioActualUseCase;
import com.condominios.sgc.application.port.in.OlvidasteContrasenaUseCase;
import com.condominios.sgc.application.port.in.RefrescarTokenUseCase;
import com.condominios.sgc.application.port.in.RestablecerContrasenaUseCase;
import com.condominios.sgc.application.port.out.TokenRepositoryPort;
import com.condominios.sgc.application.port.out.UsuarioRepositoryPort;
import com.condominios.sgc.application.port.out.service.CorreoServicePort;
import com.condominios.sgc.application.port.out.service.HashServicePort;
import com.condominios.sgc.application.port.out.service.JwtServicePort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public IniciarSesionUseCase iniciarSesionUseCase(
            UsuarioRepositoryPort usuarioRepo,
            JwtServicePort jwtService,
            HashServicePort hashService,
            TokenRepositoryPort tokenRepo) {
        return new IniciarSesionUseCaseImpl(usuarioRepo, jwtService, hashService, tokenRepo);
    }

    @Bean
    public CerrarSesionUseCase cerrarSesionUseCase(TokenRepositoryPort tokenRepo) {
        return new CerrarSesionUseCaseImpl(tokenRepo);
    }

    @Bean
    public RefrescarTokenUseCase refrescarTokenUseCase(
            TokenRepositoryPort tokenRepo,
            JwtServicePort jwtService,
            UsuarioRepositoryPort usuarioRepo) {
        return new RefrescarTokenUseCaseImpl(tokenRepo, jwtService, usuarioRepo);
    }

    @Bean
    public OlvidasteContrasenaUseCase olvidasteContrasenaUseCase(
            UsuarioRepositoryPort usuarioRepo,
            JwtServicePort jwtService,
            TokenRepositoryPort tokenRepo,
            CorreoServicePort correoService) {
        return new OlvidasteContrasenaUseCaseImpl(usuarioRepo, jwtService, tokenRepo, correoService);
    }

    @Bean
    public RestablecerContrasenaUseCase restablecerContrasenaUseCase(
            TokenRepositoryPort tokenRepo,
            JwtServicePort jwtService,
            HashServicePort hashService,
            UsuarioRepositoryPort usuarioRepo) {
        return new RestablecerContrasenaUseCaseImpl(tokenRepo, jwtService, hashService, usuarioRepo);
    }

    @Bean
    public ObtenerUsuarioActualUseCase obtenerUsuarioActualUseCase(UsuarioRepositoryPort usuarioRepo) {
        return new ObtenerUsuarioActualUseCaseImpl(usuarioRepo);
    }
}

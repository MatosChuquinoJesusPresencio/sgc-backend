package com.condominios.sgc.infrastructure.config;

import com.condominios.sgc.application.impl.autenticacion.*;
import com.condominios.sgc.application.usecase.autenticacion.*;
import com.condominios.sgc.domain.port.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AutenticacionConfig {

    @Bean
    public CambiarContrasenaUseCase cambiarContrasenaUseCase(UsuarioPort usuarioPort, AutenticacionPort autenticacionPort) {
        return new CambiarContrasenaUseCaseImpl(usuarioPort, autenticacionPort);
    }

    @Bean
    public CerrarSesionUseCase cerrarSesionUseCase(TokenPort tokenPort) {
        return new CerrarSesionUseCaseImpl(tokenPort);
    }

    @Bean
    public ConfirmarCorreoUseCase confirmarCorreoUseCase(TokenPort tokenPort, UsuarioPort usuarioPort) {
        return new ConfirmarCorreoUseCaseImpl(tokenPort, usuarioPort);
    }

    @Bean
    public IniciarSesionUseCase iniciarSesionUseCase(UsuarioPort usuarioPort, AutenticacionPort autenticacionPort, TokenPort tokenPort) {
        return new IniciarSesionUseCaseImpl(usuarioPort, autenticacionPort, tokenPort);
    }

    @Bean
    public RefrescarTokenUseCase refrescarTokenUseCase(AutenticacionPort autenticacionPort, TokenPort tokenPort, UsuarioPort usuarioPort) {
        return new RefrescarTokenUseCaseImpl(autenticacionPort, tokenPort, usuarioPort);
    }

    @Bean
    public RestablecerContrasenaUseCase restablecerContrasenaUseCase(TokenPort tokenPort, UsuarioPort usuarioPort, AutenticacionPort autenticacionPort) {
        return new RestablecerContrasenaUseCaseImpl(tokenPort, usuarioPort, autenticacionPort);
    }

    @Bean
    public SolicitarReseteoContrasenaUseCase solicitarReseteoContrasenaUseCase(UsuarioPort usuarioPort, TokenPort tokenPort, CorreoPort correoPort) {
        return new SolicitarReseteoContrasenaUseCaseImpl(usuarioPort, tokenPort, correoPort);
    }

}

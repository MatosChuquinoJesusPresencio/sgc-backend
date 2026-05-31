package com.condominios.sgc.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.condominios.sgc.application.impl.CerrarSesionUseCaseImpl;
import com.condominios.sgc.application.impl.EnviarRecuperacionContrasenaUseCaseImpl;
import com.condominios.sgc.application.impl.IniciarSesionUseCaseImpl;
import com.condominios.sgc.application.impl.RefrescarTokenUseCaseImpl;
import com.condominios.sgc.application.impl.RestablecerContrasenaUseCaseImpl;
import com.condominios.sgc.application.usecase.CerrarSesionUseCase;
import com.condominios.sgc.application.usecase.EnviarRecuperacionContrasenaUseCase;
import com.condominios.sgc.application.usecase.IniciarSesionUseCase;
import com.condominios.sgc.application.usecase.RefrescarTokenUseCase;
import com.condominios.sgc.application.usecase.RestablecerContrasenaUseCase;
import com.condominios.sgc.domain.port.AutenticacionPort;
import com.condominios.sgc.domain.port.RestablecimientoTokenPort;
import com.condominios.sgc.domain.port.UsuarioPort;

@Configuration
public class AutenticacionConfig {

    @Bean
    public IniciarSesionUseCase iniciarSesionUseCase(AutenticacionPort autenticacionPort) {
        return new IniciarSesionUseCaseImpl(autenticacionPort);
    }

    @Bean
    public CerrarSesionUseCase cerrarSesionUseCase(AutenticacionPort autenticacionPort) {
        return new CerrarSesionUseCaseImpl(autenticacionPort);
    }

    @Bean
    public RefrescarTokenUseCase refrescarTokenUseCase(AutenticacionPort autenticacionPort) {
        return new RefrescarTokenUseCaseImpl(autenticacionPort);
    }

    @Bean
    public EnviarRecuperacionContrasenaUseCase enviarRecuperacionContrasenaUseCase(UsuarioPort usuarioPort, RestablecimientoTokenPort restablecimientoTokenPort) {
        return new EnviarRecuperacionContrasenaUseCaseImpl(usuarioPort, restablecimientoTokenPort);
    }

    @Bean
    public RestablecerContrasenaUseCase restablecerContrasenaUseCase(AutenticacionPort autenticacionPort, RestablecimientoTokenPort restablecimientoTokenPort) {
        return new RestablecerContrasenaUseCaseImpl(autenticacionPort, restablecimientoTokenPort);
    }
}

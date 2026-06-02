package com.condominios.sgc.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.condominios.sgc.application.impl.EnviarCorreoBienvenidaUseCaseImpl;
import com.condominios.sgc.application.impl.EnviarCorreoRecuperacionUseCaseImpl;
import com.condominios.sgc.application.impl.EnviarCorreoVerificacionUseCaseImpl;
import com.condominios.sgc.application.usecase.EnviarCorreoBienvenidaUseCase;
import com.condominios.sgc.application.usecase.EnviarCorreoRecuperacionUseCase;
import com.condominios.sgc.application.usecase.EnviarCorreoVerificacionUseCase;
import com.condominios.sgc.domain.port.CorreoPort;

@Configuration
public class CorreoConfig {

    @Bean
    public EnviarCorreoBienvenidaUseCase enviarCorreoBienvenidaUseCase(CorreoPort correoPort) {
        return new EnviarCorreoBienvenidaUseCaseImpl(correoPort);
    }

    @Bean
    public EnviarCorreoVerificacionUseCase enviarCorreoVerificacionUseCase(CorreoPort correoPort) {
        return new EnviarCorreoVerificacionUseCaseImpl(correoPort);
    }

    @Bean
    public EnviarCorreoRecuperacionUseCase enviarCorreoRecuperacionUseCase(CorreoPort correoPort) {
        return new EnviarCorreoRecuperacionUseCaseImpl(correoPort);
    }
}

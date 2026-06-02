package com.condominios.sgc.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.condominios.sgc.application.impl.ActualizarConfiguracionUseCaseImpl;
import com.condominios.sgc.application.impl.ObtenerConfiguracionUseCaseImpl;
import com.condominios.sgc.application.usecase.ActualizarConfiguracionUseCase;
import com.condominios.sgc.application.usecase.ObtenerConfiguracionUseCase;
import com.condominios.sgc.domain.port.ConfiguracionPort;

@Configuration
public class ConfiguracionConfig {

    @Bean
    public ObtenerConfiguracionUseCase obtenerConfiguracionUseCase(ConfiguracionPort configuracionPort) {
        return new ObtenerConfiguracionUseCaseImpl(configuracionPort);
    }

    @Bean
    public ActualizarConfiguracionUseCase actualizarConfiguracionUseCase(ConfiguracionPort configuracionPort) {
        return new ActualizarConfiguracionUseCaseImpl(configuracionPort);
    }
}

package com.condominios.sgc.infrastructure.config;

import com.condominios.sgc.application.impl.configuracion.*;
import com.condominios.sgc.application.usecase.configuracion.*;
import com.condominios.sgc.domain.port.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfiguracionConfig {

    @Bean
    public ActualizarConfiguracionPorCondominioUseCase actualizarConfiguracionPorCondominioUseCase(ConfiguracionPort configuracionPort) {
        return new ActualizarConfiguracionPorCondominioUseCaseImpl(configuracionPort);
    }

    @Bean
    public ObtenerConfiguracionPorCondominioUseCase obtenerConfiguracionPorCondominioUseCase(ConfiguracionPort configuracionPort) {
        return new ObtenerConfiguracionPorCondominioUseCaseImpl(configuracionPort);
    }

}

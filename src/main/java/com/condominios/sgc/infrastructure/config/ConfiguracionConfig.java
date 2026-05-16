package com.condominios.sgc.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.condominios.sgc.application.impl.ActualizarConfiguracionUseCaseImpl;
import com.condominios.sgc.application.impl.ObtenerConfiguracionUseCaseImpl;
import com.condominios.sgc.application.usecase.ActualizarConfiguracionUseCase;
import com.condominios.sgc.application.usecase.ObtenerConfiguracionUseCase;
import com.condominios.sgc.domain.port.ConfiguracionPort;
import com.condominios.sgc.infrastructure.adapter.ConfiguracionAdapter;
import com.condominios.sgc.infrastructure.persistence.repository.CondominioRepository;
import com.condominios.sgc.infrastructure.persistence.repository.ConfiguracionRepository;

@Configuration
public class ConfiguracionConfig {
    
    @Bean
    public ConfiguracionPort configuracionPort(ConfiguracionRepository repository, CondominioRepository condominioRepository) {
        return new ConfiguracionAdapter(repository, condominioRepository);
    }

    @Bean
    public ObtenerConfiguracionUseCase obtenerConfiguracionUseCase(ConfiguracionPort port) {
        return new ObtenerConfiguracionUseCaseImpl(port);
    }

    @Bean
    public ActualizarConfiguracionUseCase actualizarConfiguracionUseCase(ConfiguracionPort port) {
        return new ActualizarConfiguracionUseCaseImpl(port);
    }
}

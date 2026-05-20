package com.condominios.sgc.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.condominios.sgc.application.impl.InquilinoUseCaseImpl;
import com.condominios.sgc.domain.port.ConfiguracionPort;
import com.condominios.sgc.domain.port.InquilinoPort;

@Configuration
public class InquilinoConfig {

    @Bean
    public InquilinoUseCaseImpl inquilinoUseCaseImpl(InquilinoPort inquilinoPort, ConfiguracionPort configuracionPort) {
        return new InquilinoUseCaseImpl(inquilinoPort, configuracionPort);
    }
}

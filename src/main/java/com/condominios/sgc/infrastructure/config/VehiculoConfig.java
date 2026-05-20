package com.condominios.sgc.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.condominios.sgc.application.impl.VehiculoUseCaseImpl;
import com.condominios.sgc.domain.port.VehiculoPort;

@Configuration
public class VehiculoConfig {

    @Bean
    public VehiculoUseCaseImpl vehiculoUseCaseImpl(VehiculoPort vehiculoPort) {
        return new VehiculoUseCaseImpl(vehiculoPort);
    }
}

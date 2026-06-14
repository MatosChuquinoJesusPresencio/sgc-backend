package com.condominios.sgc.infrastructure.config;

import com.condominios.sgc.application.impl.moneda.*;
import com.condominios.sgc.application.usecase.moneda.*;
import com.condominios.sgc.domain.port.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MonedaConfig {

    @Bean
    public ObtenerMonedaPorIdUseCase obtenerMonedaPorIdUseCase(MonedaPort monedaPort) {
        return new ObtenerMonedaPorIdUseCaseImpl(monedaPort);
    }

}

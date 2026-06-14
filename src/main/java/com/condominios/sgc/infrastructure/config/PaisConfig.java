package com.condominios.sgc.infrastructure.config;

import com.condominios.sgc.application.impl.pais.*;
import com.condominios.sgc.application.usecase.pais.*;
import com.condominios.sgc.domain.port.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaisConfig {

    @Bean
    public ListarPaisesUseCase listarPaisesUseCase(PaisPort paisPort) {
        return new ListarPaisesUseCaseImpl(paisPort);
    }

    @Bean
    public ObtenerPaisPorIdUseCase obtenerPaisPorIdUseCase(PaisPort paisPort) {
        return new ObtenerPaisPorIdUseCaseImpl(paisPort);
    }

}

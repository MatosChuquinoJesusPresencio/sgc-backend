package com.condominios.sgc.infrastructure.config;

import com.condominios.sgc.application.impl.ciudad.*;
import com.condominios.sgc.application.usecase.ciudad.*;
import com.condominios.sgc.domain.port.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CiudadConfig {

    @Bean
    public ListarCiudadesPorPaisUseCase listarCiudadesPorPaisUseCase(CiudadPort ciudadPort) {
        return new ListarCiudadesPorPaisUseCaseImpl(ciudadPort);
    }

    @Bean
    public ObtenerCiudadPorIdUseCase obtenerCiudadPorIdUseCase(CiudadPort ciudadPort) {
        return new ObtenerCiudadPorIdUseCaseImpl(ciudadPort);
    }

}

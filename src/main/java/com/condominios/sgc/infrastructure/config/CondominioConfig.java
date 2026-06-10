package com.condominios.sgc.infrastructure.config;

import com.condominios.sgc.application.impl.*;
import com.condominios.sgc.application.usecase.*;
import com.condominios.sgc.domain.port.CondominioTreePort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.condominios.sgc.domain.port.CondominioPort;
import com.condominios.sgc.domain.port.ConfiguracionPort;

@Configuration
public class CondominioConfig {

    @Bean
    public CrearCondominioUseCase crearCondominioUseCase(CondominioPort condominioPort, ConfiguracionPort configuracionPort) {
        return new CrearCondominioUseCaseImpl(condominioPort, configuracionPort);
    }

    @Bean
    public ObtenerCondominioUseCase obtenerCondominioUseCase(CondominioPort condominioPort) {
        return new ObtenerCondominioUseCaseImpl(condominioPort);
    }

    @Bean
    public ActualizarCondominioUseCase actualizarCondominioUseCase(CondominioPort condominioPort) {
        return new ActualizarCondominioUseCaseImpl(condominioPort);
    }

    @Bean
    public EliminarCondominioUseCase eliminarCondominioUseCase(CondominioPort condominioPort) {
        return new EliminarCondominioUseCaseImpl(condominioPort);
    }

    @Bean
    public ListarCondominiosUseCase listarCondominiosUseCase(CondominioPort condominioPort) {
        return new ListarCondominiosUseCaseImpl(condominioPort);
    }

    @Bean
    public ObtenerArbolCondominioUseCase obtenerArbolCondominioUseCase(CondominioTreePort condominioTreePort) {
        return new ObtenerArbolCondominioUseCaseImpl(condominioTreePort);
    }
}

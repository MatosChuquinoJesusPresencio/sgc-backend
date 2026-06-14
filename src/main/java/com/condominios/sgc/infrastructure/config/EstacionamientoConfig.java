package com.condominios.sgc.infrastructure.config;

import com.condominios.sgc.application.impl.estacionamiento.*;
import com.condominios.sgc.application.usecase.estacionamiento.*;
import com.condominios.sgc.domain.port.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EstacionamientoConfig {

    @Bean
    public ActualizarEstacionamientoPorIdUseCase actualizarEstacionamientoPorIdUseCase(EstacionamientoPort estacionamientoPort, ConfiguracionPort configuracionPort) {
        return new ActualizarEstacionamientoPorIdUseCaseImpl(estacionamientoPort, configuracionPort);
    }

    @Bean
    public CrearEstacionamientoUseCase crearEstacionamientoUseCase(EstacionamientoPort estacionamientoPort) {
        return new CrearEstacionamientoUseCaseImpl(estacionamientoPort);
    }

    @Bean
    public EliminarEstacionamientoPorIdUseCase eliminarEstacionamientoPorIdUseCase(EstacionamientoPort estacionamientoPort) {
        return new EliminarEstacionamientoPorIdUseCaseImpl(estacionamientoPort);
    }

    @Bean
    public ListarEstacionamientosUseCase listarEstacionamientosUseCase(EstacionamientoPort estacionamientoPort) {
        return new ListarEstacionamientosUseCaseImpl(estacionamientoPort);
    }

    @Bean
    public ObtenerEstacionamientoPorIdUseCase obtenerEstacionamientoPorIdUseCase(EstacionamientoPort estacionamientoPort) {
        return new ObtenerEstacionamientoPorIdUseCaseImpl(estacionamientoPort);
    }

}

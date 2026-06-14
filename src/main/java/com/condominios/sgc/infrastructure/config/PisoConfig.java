package com.condominios.sgc.infrastructure.config;

import com.condominios.sgc.application.impl.piso.*;
import com.condominios.sgc.application.usecase.piso.*;
import com.condominios.sgc.domain.port.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PisoConfig {

    @Bean
    public ActualizarPisoPorIdUseCase actualizarPisoPorIdUseCase(PisoPort pisoPort) {
        return new ActualizarPisoPorIdUseCaseImpl(pisoPort);
    }

    @Bean
    public CrearPisoUseCase crearPisoUseCase(PisoPort pisoPort) {
        return new CrearPisoUseCaseImpl(pisoPort);
    }

    @Bean
    public EliminarPisoPorIdUseCase eliminarPisoPorIdUseCase(PisoPort pisoPort) {
        return new EliminarPisoPorIdUseCaseImpl(pisoPort);
    }

    @Bean
    public ListarPisosUseCase listarPisosUseCase(PisoPort pisoPort) {
        return new ListarPisosUseCaseImpl(pisoPort);
    }

    @Bean
    public ObtenerPisoPorIdUseCase obtenerPisoPorIdUseCase(PisoPort pisoPort) {
        return new ObtenerPisoPorIdUseCaseImpl(pisoPort);
    }

}

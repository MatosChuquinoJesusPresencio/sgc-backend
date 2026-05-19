package com.condominios.sgc.infrastructure.config;

import com.condominios.sgc.application.impl.*;
import com.condominios.sgc.application.usecase.*;
import com.condominios.sgc.domain.port.PisoPort;
import com.condominios.sgc.domain.port.TorrePort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PisoConfig {

    @Bean
    public CrearPisoUseCase crearPisoUseCase(PisoPort pisoPort, TorrePort torrePort) {
        return new CrearPisoUseCaseImpl(pisoPort, torrePort);
    }

    @Bean
    public ObtenerPisoUseCase obtenerPisoUseCase(PisoPort pisoPort) {
        return new ObtenerPisoUseCaseImpl(pisoPort);
    }

    @Bean
    public ListarPisosPorTorreUseCase listarPisosPorTorreUseCase(PisoPort pisoPort, TorrePort torrePort) {
        return new ListarPisosPorTorreUseCaseImpl(pisoPort, torrePort);
    }

    @Bean
    public ActualizarPisoUseCase actualizarPisoUseCase(PisoPort pisoPort, ObtenerPisoUseCase obtenerPisoUseCase) {
        return new ActualizarPisoUseCaseImpl(pisoPort, obtenerPisoUseCase);
    }

    @Bean
    public EliminarPisoUseCase eliminarPisoUseCase(PisoPort pisoPort, ObtenerPisoUseCase obtenerPisoUseCase) {
        return new EliminarPisoUseCaseImpl(pisoPort, obtenerPisoUseCase);
    }

}
package com.condominios.sgc.infrastructure.config;

import com.condominios.sgc.application.impl.torre.*;
import com.condominios.sgc.application.usecase.torre.*;
import com.condominios.sgc.domain.port.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TorreConfig {

    @Bean
    public ActualizarTorrePorIdUseCase actualizarTorrePorIdUseCase(TorrePort torrePort) {
        return new ActualizarTorrePorIdUseCaseImpl(torrePort);
    }

    @Bean
    public CrearTorreUseCase crearTorreUseCase(TorrePort torrePort) {
        return new CrearTorreUseCaseImpl(torrePort);
    }

    @Bean
    public EliminarTorrePorIdUseCase eliminarTorrePorIdUseCase(TorrePort torrePort) {
        return new EliminarTorrePorIdUseCaseImpl(torrePort);
    }

    @Bean
    public ListarTorresUseCase listarTorresUseCase(TorrePort torrePort) {
        return new ListarTorresUseCaseImpl(torrePort);
    }

    @Bean
    public ObtenerTorrePorIdUseCase obtenerTorrePorIdUseCase(TorrePort torrePort) {
        return new ObtenerTorrePorIdUseCaseImpl(torrePort);
    }

}

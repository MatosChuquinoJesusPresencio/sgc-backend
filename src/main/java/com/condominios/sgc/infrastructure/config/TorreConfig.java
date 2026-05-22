package com.condominios.sgc.infrastructure.config;

import com.condominios.sgc.application.impl.*;
import com.condominios.sgc.application.usecase.*;
import com.condominios.sgc.domain.port.CondominioPort;
import com.condominios.sgc.domain.port.TorrePort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TorreConfig {

    @Bean
    public CrearTorreUseCase crearTorreUseCase(TorrePort torrePort, CondominioPort condominioPort) {
        return new CrearTorreUseCaseImpl(torrePort, condominioPort);
    }

    @Bean
    public ObtenerTorreUseCase obtenerTorreUseCase(TorrePort torrePort) {
        return new ObtenerTorreUseCaseImpl(torrePort);
    }

    @Bean
    public ListarTorresPorCondominioUseCase listarTorresPorCondominioUseCase(TorrePort torrePort, CondominioPort condominioPort) {
        return new ListarTorresPorCondominioUseCaseImpl(torrePort, condominioPort);
    }

    @Bean
    public ActualizarTorreUseCase actualizarTorreUseCase(TorrePort torrePort, ObtenerTorreUseCase obtenerTorreUseCase) {
        return new ActualizarTorreUseCaseImpl(torrePort, obtenerTorreUseCase);
    }

    @Bean
    public EliminarTorreUseCase eliminarTorreUseCase(TorrePort torrePort, ObtenerTorreUseCase obtenerTorreUseCase) {
        return new EliminarTorreUseCaseImpl(torrePort, obtenerTorreUseCase);
    }
}
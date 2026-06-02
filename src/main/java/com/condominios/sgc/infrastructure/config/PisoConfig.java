package com.condominios.sgc.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.condominios.sgc.application.impl.ActualizarPisoUseCaseImpl;
import com.condominios.sgc.application.impl.CrearPisoUseCaseImpl;
import com.condominios.sgc.application.impl.EliminarPisoUseCaseImpl;
import com.condominios.sgc.application.impl.ListarPisosPorTorreUseCaseImpl;
import com.condominios.sgc.application.impl.ObtenerPisoUseCaseImpl;
import com.condominios.sgc.application.usecase.ActualizarPisoUseCase;
import com.condominios.sgc.application.usecase.CrearPisoUseCase;
import com.condominios.sgc.application.usecase.EliminarPisoUseCase;
import com.condominios.sgc.application.usecase.ListarPisosPorTorreUseCase;
import com.condominios.sgc.application.usecase.ObtenerPisoUseCase;
import com.condominios.sgc.domain.port.PisoPort;
import com.condominios.sgc.domain.port.TorrePort;

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
    public ActualizarPisoUseCase actualizarPisoUseCase(PisoPort pisoPort) {
        return new ActualizarPisoUseCaseImpl(pisoPort);
    }

    @Bean
    public EliminarPisoUseCase eliminarPisoUseCase(PisoPort pisoPort) {
        return new EliminarPisoUseCaseImpl(pisoPort);
    }

    @Bean
    public ListarPisosPorTorreUseCase listarPisosPorTorreUseCase(PisoPort pisoPort, TorrePort torrePort) {
        return new ListarPisosPorTorreUseCaseImpl(pisoPort, torrePort);
    }
}

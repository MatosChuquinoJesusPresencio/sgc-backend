package com.condominios.sgc.infrastructure.config;

import com.condominios.sgc.application.impl.*;
import com.condominios.sgc.application.usecase.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.condominios.sgc.domain.port.ApartamentoPort;
import com.condominios.sgc.domain.port.PisoPort;

@Configuration
public class ApartamentoConfig {

    @Bean
    public CrearApartamentoUseCase crearApartamentoUseCase(ApartamentoPort apartamentoPort, PisoPort pisoPort) {
        return new CrearApartamentoUseCaseImpl(apartamentoPort, pisoPort);
    }

    @Bean
    public ObtenerApartamentoUseCase obtenerApartamentoUseCase(ApartamentoPort apartamentoPort) {
        return new ObtenerApartamentoUseCaseImpl(apartamentoPort);
    }

    @Bean
    public ActualizarApartamentoUseCase actualizarApartamentoUseCase(ApartamentoPort apartamentoPort) {
        return new ActualizarApartamentoUseCaseImpl(apartamentoPort);
    }

    @Bean
    public EliminarApartamentoUseCase eliminarApartamentoUseCase(ApartamentoPort apartamentoPort) {
        return new EliminarApartamentoUseCaseImpl(apartamentoPort);
    }

    @Bean
    public ListarApartamentosPorPisoUseCase listarApartamentosPorPisoUseCase(ApartamentoPort apartamentoPort, PisoPort pisoPort) {
        return new ListarApartamentosPorPisoUseCaseImpl(apartamentoPort, pisoPort);
    }

    @Bean
    public ListarApartamentosFiltradosUseCase listarApartamentosFiltradosUseCase(ApartamentoPort apartamentoPort) {
        return new ListarApartamentosFiltradosUseCaseImpl(apartamentoPort);
    }
}

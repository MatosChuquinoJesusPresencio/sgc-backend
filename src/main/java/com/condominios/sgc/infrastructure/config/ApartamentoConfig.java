package com.condominios.sgc.infrastructure.config;

import com.condominios.sgc.application.impl.*;
import com.condominios.sgc.application.usecase.*;
import com.condominios.sgc.domain.port.ApartamentoPort;
import com.condominios.sgc.domain.port.PisoPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    public ListarApartamentosPorPisoUseCase listarApartamentosPorPisoUseCase(ApartamentoPort apartamentoPort, PisoPort pisoPort) {
        return new ListarApartamentosPorPisoUseCaseImpl(apartamentoPort, pisoPort);
    }

    @Bean
    public ActualizarApartamentoUseCase actualizarApartamentoUseCase(ApartamentoPort apartamentoPort, ObtenerApartamentoUseCase obtenerApartamentoUseCase) {
        return new ActualizarApartamentoUseCaseImpl(apartamentoPort, obtenerApartamentoUseCase);
    }

    @Bean
    public EliminarApartamentoUseCase eliminarApartamentoUseCase(ApartamentoPort apartamentoPort, ObtenerApartamentoUseCase obtenerApartamentoUseCase) {
        return new EliminarApartamentoUseCaseImpl(apartamentoPort, obtenerApartamentoUseCase);
    }
}
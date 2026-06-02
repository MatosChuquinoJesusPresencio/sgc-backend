package com.condominios.sgc.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.condominios.sgc.application.impl.ActualizarApartamentoUseCaseImpl;
import com.condominios.sgc.application.impl.CrearApartamentoUseCaseImpl;
import com.condominios.sgc.application.impl.EliminarApartamentoUseCaseImpl;
import com.condominios.sgc.application.impl.ListarApartamentosPorPisoUseCaseImpl;
import com.condominios.sgc.application.impl.ObtenerApartamentoUseCaseImpl;
import com.condominios.sgc.application.usecase.ActualizarApartamentoUseCase;
import com.condominios.sgc.application.usecase.CrearApartamentoUseCase;
import com.condominios.sgc.application.usecase.EliminarApartamentoUseCase;
import com.condominios.sgc.application.usecase.ListarApartamentosPorPisoUseCase;
import com.condominios.sgc.application.usecase.ObtenerApartamentoUseCase;
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
}

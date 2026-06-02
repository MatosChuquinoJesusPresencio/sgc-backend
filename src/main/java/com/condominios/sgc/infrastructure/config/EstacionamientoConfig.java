package com.condominios.sgc.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.condominios.sgc.application.impl.ActualizarEstacionamientoUseCaseImpl;
import com.condominios.sgc.application.impl.ConfigurarEstacionamientoUseCaseImpl;
import com.condominios.sgc.application.impl.CrearEstacionamientoUseCaseImpl;
import com.condominios.sgc.application.impl.EliminarEstacionamientoUseCaseImpl;
import com.condominios.sgc.application.impl.ListarEstacionamientosPorApartamentoUseCaseImpl;
import com.condominios.sgc.application.impl.ListarEstacionamientosPorCondominioUseCaseImpl;
import com.condominios.sgc.application.impl.ObtenerEstacionamientoUseCaseImpl;
import com.condominios.sgc.application.usecase.ActualizarEstacionamientoUseCase;
import com.condominios.sgc.application.usecase.ConfigurarEstacionamientoUseCase;
import com.condominios.sgc.application.usecase.CrearEstacionamientoUseCase;
import com.condominios.sgc.application.usecase.EliminarEstacionamientoUseCase;
import com.condominios.sgc.application.usecase.ListarEstacionamientosPorApartamentoUseCase;
import com.condominios.sgc.application.usecase.ListarEstacionamientosPorCondominioUseCase;
import com.condominios.sgc.application.usecase.ObtenerEstacionamientoUseCase;
import com.condominios.sgc.domain.port.EstacionamientoPort;

@Configuration
public class EstacionamientoConfig {

    @Bean
    public CrearEstacionamientoUseCase crearEstacionamientoUseCase(EstacionamientoPort estacionamientoPort) {
        return new CrearEstacionamientoUseCaseImpl(estacionamientoPort);
    }

    @Bean
    public ObtenerEstacionamientoUseCase obtenerEstacionamientoUseCase(EstacionamientoPort estacionamientoPort) {
        return new ObtenerEstacionamientoUseCaseImpl(estacionamientoPort);
    }

    @Bean
    public ActualizarEstacionamientoUseCase actualizarEstacionamientoUseCase(EstacionamientoPort estacionamientoPort) {
        return new ActualizarEstacionamientoUseCaseImpl(estacionamientoPort);
    }

    @Bean
    public EliminarEstacionamientoUseCase eliminarEstacionamientoUseCase(EstacionamientoPort estacionamientoPort) {
        return new EliminarEstacionamientoUseCaseImpl(estacionamientoPort);
    }

    @Bean
    public ConfigurarEstacionamientoUseCase configurarEstacionamientoUseCase(EstacionamientoPort estacionamientoPort) {
        return new ConfigurarEstacionamientoUseCaseImpl(estacionamientoPort);
    }

    @Bean
    public ListarEstacionamientosPorCondominioUseCase listarEstacionamientosPorCondominioUseCase(EstacionamientoPort estacionamientoPort) {
        return new ListarEstacionamientosPorCondominioUseCaseImpl(estacionamientoPort);
    }

    @Bean
    public ListarEstacionamientosPorApartamentoUseCase listarEstacionamientosPorApartamentoUseCase(EstacionamientoPort estacionamientoPort) {
        return new ListarEstacionamientosPorApartamentoUseCaseImpl(estacionamientoPort);
    }
}

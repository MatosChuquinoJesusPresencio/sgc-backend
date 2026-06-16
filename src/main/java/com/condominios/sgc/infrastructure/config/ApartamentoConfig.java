package com.condominios.sgc.infrastructure.config;

import com.condominios.sgc.application.impl.apartamento.*;
import com.condominios.sgc.application.usecase.apartamento.*;
import com.condominios.sgc.domain.port.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApartamentoConfig {

    @Bean
    public ActualizarApartamentoPorIdUseCase actualizarApartamentoPorIdUseCase(ApartamentoPort apartamentoPort) {
        return new ActualizarApartamentoPorIdUseCaseImpl(apartamentoPort);
    }

    @Bean
    public CrearApartamentoUseCase crearApartamentoUseCase(ApartamentoPort apartamentoPort) {
        return new CrearApartamentoUseCaseImpl(apartamentoPort);
    }

    @Bean
    public EliminarApartamentoPorIdUseCase eliminarApartamentoPorIdUseCase(ApartamentoPort apartamentoPort) {
        return new EliminarApartamentoPorIdUseCaseImpl(apartamentoPort);
    }

    @Bean
    public ListarApartamentosUseCase listarApartamentosUseCase(ApartamentoPort apartamentoPort) {
        return new ListarApartamentosUseCaseImpl(apartamentoPort);
    }

    @Bean
    public ObtenerApartamentoPorIdUseCase obtenerApartamentoPorIdUseCase(ApartamentoPort apartamentoPort) {
        return new ObtenerApartamentoPorIdUseCaseImpl(apartamentoPort);
    }

    @Bean
    public ObtenerApartamentoPorPropietarioUseCase obtenerApartamentoPorPropietarioUseCase(ApartamentoPort apartamentoPort) {
        return new ObtenerApartamentoPorPropietarioUseCaseImpl(apartamentoPort);
    }

    @Bean
    public ObtenerDetalleApartamentoUseCase obtenerDetalleApartamentoUseCase(ApartamentoPort apartamentoPort,
            PisoPort pisoPort, TorrePort torrePort, CondominioPort condominioPort,
            UsuarioPort usuarioPort, InquilinoPort inquilinoPort, EstacionamientoPort estacionamientoPort) {
        return new ObtenerDetalleApartamentoUseCaseImpl(apartamentoPort, pisoPort, torrePort,
                condominioPort, usuarioPort, inquilinoPort, estacionamientoPort);
    }

}

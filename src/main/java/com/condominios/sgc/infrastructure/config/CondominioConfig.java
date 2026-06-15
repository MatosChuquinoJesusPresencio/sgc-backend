package com.condominios.sgc.infrastructure.config;

import com.condominios.sgc.application.impl.condominio.*;
import com.condominios.sgc.application.usecase.condominio.*;
import com.condominios.sgc.domain.port.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CondominioConfig {

    @Bean
    public ActualizarCondominioPorIdUseCase actualizarCondominioPorIdUseCase(CondominioPort condominioPort) {
        return new ActualizarCondominioPorIdUseCaseImpl(condominioPort);
    }

    @Bean
    public CrearCondominioUseCase crearCondominioUseCase(CondominioPort condominioPort, ConfiguracionPort configuracionPort) {
        return new CrearCondominioUseCaseImpl(condominioPort, configuracionPort);
    }

    @Bean
    public EliminarCondominioPorIdUseCase eliminarCondominioPorIdUseCase(CondominioPort condominioPort) {
        return new EliminarCondominioPorIdUseCaseImpl(condominioPort);
    }

    @Bean
    public ListarCondominiosUseCase listarCondominiosUseCase(CondominioPort condominioPort) {
        return new ListarCondominiosUseCaseImpl(condominioPort);
    }

    @Bean
    public ObtenerCondominioPorIdUseCase obtenerCondominioPorIdUseCase(CondominioPort condominioPort) {
        return new ObtenerCondominioPorIdUseCaseImpl(condominioPort);
    }

    @Bean
    public ObtenerDetalleCondominioUseCase obtenerDetalleCondominioUseCase(CondominioPort condominioPort,
                                                                           TorrePort torrePort,
                                                                           PisoPort pisoPort,
                                                                           ApartamentoPort apartamentoPort,
                                                                           EstacionamientoPort estacionamientoPort,
                                                                           CarritoPort carritoPort,
                                                                           UsuarioPort usuarioPort,
                                                                           ConfiguracionPort configuracionPort) {
        return new ObtenerDetalleCondominioUseCaseImpl(condominioPort, torrePort, pisoPort,
                apartamentoPort, estacionamientoPort, carritoPort, usuarioPort, configuracionPort);
    }

}

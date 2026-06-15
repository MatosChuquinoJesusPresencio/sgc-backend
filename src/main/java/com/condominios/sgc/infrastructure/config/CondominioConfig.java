package com.condominios.sgc.infrastructure.config;

import com.condominios.sgc.application.impl.condominio.*;
import com.condominios.sgc.application.usecase.condominio.*;
import com.condominios.sgc.domain.port.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CondominioConfig {

    @Bean
    public ActualizarCondominioPorIdUseCase actualizarCondominioPorIdUseCase(CondominioPort condominioPort,
            PaisPort paisPort,
            CiudadPort ciudadPort) {
        return new ActualizarCondominioPorIdUseCaseImpl(condominioPort, paisPort, ciudadPort);
    }

    @Bean
    public CrearCondominioUseCase crearCondominioUseCase(CondominioPort condominioPort,
            ConfiguracionPort configuracionPort,
            PaisPort paisPort,
            CiudadPort ciudadPort) {
        return new CrearCondominioUseCaseImpl(condominioPort, configuracionPort, paisPort, ciudadPort);
    }

    @Bean
    public EliminarCondominioPorIdUseCase eliminarCondominioPorIdUseCase(CondominioPort condominioPort) {
        return new EliminarCondominioPorIdUseCaseImpl(condominioPort);
    }

    @Bean
    public ListarCondominiosUseCase listarCondominiosUseCase(CondominioPort condominioPort,
            PaisPort paisPort,
            CiudadPort ciudadPort) {
        return new ListarCondominiosUseCaseImpl(condominioPort, paisPort, ciudadPort);
    }

    @Bean
    public ObtenerCondominioPorIdUseCase obtenerCondominioPorIdUseCase(CondominioPort condominioPort,
            PaisPort paisPort,
            CiudadPort ciudadPort) {
        return new ObtenerCondominioPorIdUseCaseImpl(condominioPort, paisPort, ciudadPort);
    }

    @Bean
    public ObtenerDetalleCondominioUseCase obtenerDetalleCondominioUseCase(CondominioPort condominioPort,
            TorrePort torrePort,
            PisoPort pisoPort,
            ApartamentoPort apartamentoPort,
            EstacionamientoPort estacionamientoPort,
            CarritoPort carritoPort,
            UsuarioPort usuarioPort,
            ConfiguracionPort configuracionPort,
            PaisPort paisPort,
            CiudadPort ciudadPort) {
        return new ObtenerDetalleCondominioUseCaseImpl(condominioPort, torrePort, pisoPort,
                apartamentoPort, estacionamientoPort, carritoPort, usuarioPort, configuracionPort,
                paisPort, ciudadPort);
    }

}

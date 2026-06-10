package com.condominios.sgc.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.condominios.sgc.application.impl.ActualizarCondominioUseCaseImpl;
import com.condominios.sgc.application.impl.CrearCondominioUseCaseImpl;
import com.condominios.sgc.application.impl.EliminarCondominioUseCaseImpl;
import com.condominios.sgc.application.impl.ListarCondominiosUseCaseImpl;
import com.condominios.sgc.application.impl.ObtenerCondominioRelationsUseCaseImpl;
import com.condominios.sgc.application.impl.ObtenerCondominioUseCaseImpl;
import com.condominios.sgc.application.usecase.ActualizarCondominioUseCase;
import com.condominios.sgc.application.usecase.CrearCondominioUseCase;
import com.condominios.sgc.application.usecase.EliminarCondominioUseCase;
import com.condominios.sgc.application.usecase.ListarCondominiosUseCase;
import com.condominios.sgc.application.usecase.ObtenerCondominioRelationsUseCase;
import com.condominios.sgc.application.usecase.ObtenerCondominioUseCase;
import com.condominios.sgc.domain.port.CarritoPort;
import com.condominios.sgc.domain.port.CondominioPort;
import com.condominios.sgc.domain.port.ConfiguracionPort;
import com.condominios.sgc.domain.port.TorrePort;
import com.condominios.sgc.domain.port.UsuarioPort;

@Configuration
public class CondominioConfig {

    @Bean
    public CrearCondominioUseCase crearCondominioUseCase(CondominioPort condominioPort, ConfiguracionPort configuracionPort) {
        return new CrearCondominioUseCaseImpl(condominioPort, configuracionPort);
    }

    @Bean
    public ObtenerCondominioUseCase obtenerCondominioUseCase(CondominioPort condominioPort) {
        return new ObtenerCondominioUseCaseImpl(condominioPort);
    }

    @Bean
    public ActualizarCondominioUseCase actualizarCondominioUseCase(CondominioPort condominioPort) {
        return new ActualizarCondominioUseCaseImpl(condominioPort);
    }

    @Bean
    public EliminarCondominioUseCase eliminarCondominioUseCase(CondominioPort condominioPort) {
        return new EliminarCondominioUseCaseImpl(condominioPort);
    }

    @Bean
    public ListarCondominiosUseCase listarCondominiosUseCase(CondominioPort condominioPort) {
        return new ListarCondominiosUseCaseImpl(condominioPort);
    }

    @Bean
    public ObtenerCondominioRelationsUseCase obtenerCondominioRelationsUseCase(
            TorrePort torrePort,
            UsuarioPort usuarioPort,
            CarritoPort carritoPort,
            ConfiguracionPort configuracionPort) {
        return new ObtenerCondominioRelationsUseCaseImpl(
                torrePort, usuarioPort, carritoPort, configuracionPort);
    }
}

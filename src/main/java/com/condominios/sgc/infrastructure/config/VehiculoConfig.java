package com.condominios.sgc.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.condominios.sgc.application.impl.VehiculoUseCaseImpl;
import com.condominios.sgc.application.usecase.CrearVehiculoUseCase;
import com.condominios.sgc.application.usecase.EliminarVehiculoUseCase;
import com.condominios.sgc.application.usecase.ListarVehiculosUseCase;
import com.condominios.sgc.application.usecase.ObtenerVehiculoUseCase;
import com.condominios.sgc.domain.port.VehiculoPort;

@Configuration
public class VehiculoConfig {

    @Bean
    public VehiculoUseCaseImpl vehiculoUseCaseImpl(VehiculoPort vehiculoPort) {
        return new VehiculoUseCaseImpl(vehiculoPort);
    }

    @Bean
    public CrearVehiculoUseCase crearVehiculoUseCase(VehiculoUseCaseImpl impl) {
        return impl;
    }

    @Bean
    public ObtenerVehiculoUseCase obtenerVehiculoUseCase(VehiculoUseCaseImpl impl) {
        return impl;
    }

    @Bean
    public ListarVehiculosUseCase listarVehiculosUseCase(VehiculoUseCaseImpl impl) {
        return impl;
    }

    @Bean
    public EliminarVehiculoUseCase eliminarVehiculoUseCase(VehiculoUseCaseImpl impl) {
        return impl;
    }
}

package com.condominios.sgc.infrastructure.config;

import com.condominios.sgc.application.impl.vehiculo.*;
import com.condominios.sgc.application.usecase.vehiculo.*;
import com.condominios.sgc.domain.port.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VehiculoConfig {

    @Bean
    public ActualizarVehiculoPorIdUseCase actualizarVehiculoPorIdUseCase(VehiculoPort vehiculoPort, ConfiguracionPort configuracionPort) {
        return new ActualizarVehiculoPorIdUseCaseImpl(vehiculoPort, configuracionPort);
    }

    @Bean
    public CrearVehiculoUseCase crearVehiculoUseCase(VehiculoPort vehiculoPort, ConfiguracionPort configuracionPort) {
        return new CrearVehiculoUseCaseImpl(vehiculoPort, configuracionPort);
    }

    @Bean
    public EliminarVehiculoPorIdUseCase eliminarVehiculoPorIdUseCase(VehiculoPort vehiculoPort) {
        return new EliminarVehiculoPorIdUseCaseImpl(vehiculoPort);
    }

    @Bean
    public ListarVehiculosUseCase listarVehiculosUseCase(VehiculoPort vehiculoPort) {
        return new ListarVehiculosUseCaseImpl(vehiculoPort);
    }

    @Bean
    public ObtenerVehiculoPorIdUseCase obtenerVehiculoPorIdUseCase(VehiculoPort vehiculoPort) {
        return new ObtenerVehiculoPorIdUseCaseImpl(vehiculoPort);
    }

    @Bean
    public ObtenerVehiculoPorPlacaUseCase obtenerVehiculoPorPlacaUseCase(VehiculoPort vehiculoPort) {
        return new ObtenerVehiculoPorPlacaUseCaseImpl(vehiculoPort);
    }

}

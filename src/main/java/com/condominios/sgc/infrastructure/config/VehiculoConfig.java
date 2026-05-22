package com.condominios.sgc.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.condominios.sgc.application.impl.ActualizarVehiculoUseCaseImpl;
import com.condominios.sgc.application.impl.CrearVehiculoUseCaseImpl;
import com.condominios.sgc.application.impl.EliminarVehiculoUseCaseImpl;
import com.condominios.sgc.application.impl.ListarVehiculosUseCaseImpl;
import com.condominios.sgc.application.impl.ObtenerVehiculoUseCaseImpl;
import com.condominios.sgc.application.usecase.ActualizarVehiculoUseCase;
import com.condominios.sgc.application.usecase.CrearVehiculoUseCase;
import com.condominios.sgc.application.usecase.EliminarVehiculoUseCase;
import com.condominios.sgc.application.usecase.ListarVehiculosUseCase;
import com.condominios.sgc.application.usecase.ObtenerVehiculoUseCase;
import com.condominios.sgc.domain.port.VehiculoPort;

@Configuration
public class VehiculoConfig {

    @Bean
    public CrearVehiculoUseCase crearVehiculoUseCase(VehiculoPort vehiculoPort) {
        return new CrearVehiculoUseCaseImpl(vehiculoPort);
    }

    @Bean
    public ObtenerVehiculoUseCase obtenerVehiculoUseCase(VehiculoPort vehiculoPort) {
        return new ObtenerVehiculoUseCaseImpl(vehiculoPort);
    }

    @Bean
    public ListarVehiculosUseCase listarVehiculosUseCase(VehiculoPort vehiculoPort) {
        return new ListarVehiculosUseCaseImpl(vehiculoPort);
    }

    @Bean
    public EliminarVehiculoUseCase eliminarVehiculoUseCase(VehiculoPort vehiculoPort) {
        return new EliminarVehiculoUseCaseImpl(vehiculoPort);
    }

    @Bean
    public ActualizarVehiculoUseCase actualizarVehiculoUseCase(VehiculoPort vehiculoPort) {
        return new ActualizarVehiculoUseCaseImpl(vehiculoPort);
    }
}

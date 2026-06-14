package com.condominios.sgc.infrastructure.config;

import com.condominios.sgc.application.impl.carrito.*;
import com.condominios.sgc.application.usecase.carrito.*;
import com.condominios.sgc.domain.port.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CarritoConfig {

    @Bean
    public ActualizarCarritoPorIdUseCase actualizarCarritoPorIdUseCase(CarritoPort carritoPort) {
        return new ActualizarCarritoPorIdUseCaseImpl(carritoPort);
    }

    @Bean
    public CambiarEstadoCarritoPorIdUseCase cambiarEstadoCarritoPorIdUseCase(CarritoPort carritoPort) {
        return new CambiarEstadoCarritoPorIdUseCaseImpl(carritoPort);
    }

    @Bean
    public CrearCarritoUseCase crearCarritoUseCase(CarritoPort carritoPort) {
        return new CrearCarritoUseCaseImpl(carritoPort);
    }

    @Bean
    public EliminarCarritoPorIdUseCase eliminarCarritoPorIdUseCase(CarritoPort carritoPort) {
        return new EliminarCarritoPorIdUseCaseImpl(carritoPort);
    }

    @Bean
    public ListarCarritosUseCase listarCarritosUseCase(CarritoPort carritoPort) {
        return new ListarCarritosUseCaseImpl(carritoPort);
    }

    @Bean
    public ObtenerCarritoPorCodigoUseCase obtenerCarritoPorCodigoUseCase(CarritoPort carritoPort) {
        return new ObtenerCarritoPorCodigoUseCaseImpl(carritoPort);
    }

    @Bean
    public ObtenerCarritoPorIdUseCase obtenerCarritoPorIdUseCase(CarritoPort carritoPort) {
        return new ObtenerCarritoPorIdUseCaseImpl(carritoPort);
    }

}

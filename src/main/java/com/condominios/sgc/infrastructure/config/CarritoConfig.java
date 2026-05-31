package com.condominios.sgc.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.condominios.sgc.application.impl.ActualizarCarritoUseCaseImpl;
import com.condominios.sgc.application.impl.CambiarEstadoCarritoUseCaseImpl;
import com.condominios.sgc.application.impl.CrearCarritoUseCaseImpl;
import com.condominios.sgc.application.impl.EliminarCarritoUseCaseImpl;
import com.condominios.sgc.application.impl.ListarCarritosPorCondominioUseCaseImpl;
import com.condominios.sgc.application.impl.ObtenerCarritoUseCaseImpl;
import com.condominios.sgc.application.usecase.ActualizarCarritoUseCase;
import com.condominios.sgc.application.usecase.CambiarEstadoCarritoUseCase;
import com.condominios.sgc.application.usecase.CrearCarritoUseCase;
import com.condominios.sgc.application.usecase.EliminarCarritoUseCase;
import com.condominios.sgc.application.usecase.ListarCarritosPorCondominioUseCase;
import com.condominios.sgc.application.usecase.ObtenerCarritoUseCase;
import com.condominios.sgc.domain.port.CarritoPort;

@Configuration
public class CarritoConfig {

    @Bean
    public CrearCarritoUseCase crearCarritoUseCase(CarritoPort carritoPort) {
        return new CrearCarritoUseCaseImpl(carritoPort);
    }

    @Bean
    public ObtenerCarritoUseCase obtenerCarritoUseCase(CarritoPort carritoPort) {
        return new ObtenerCarritoUseCaseImpl(carritoPort);
    }

    @Bean
    public ActualizarCarritoUseCase actualizarCarritoUseCase(CarritoPort carritoPort) {
        return new ActualizarCarritoUseCaseImpl(carritoPort);
    }

    @Bean
    public EliminarCarritoUseCase eliminarCarritoUseCase(CarritoPort carritoPort) {
        return new EliminarCarritoUseCaseImpl(carritoPort);
    }

    @Bean
    public CambiarEstadoCarritoUseCase cambiarEstadoCarritoUseCase(CarritoPort carritoPort) {
        return new CambiarEstadoCarritoUseCaseImpl(carritoPort);
    }

    @Bean
    public ListarCarritosPorCondominioUseCase listarCarritosPorCondominioUseCase(CarritoPort carritoPort) {
        return new ListarCarritosPorCondominioUseCaseImpl(carritoPort);
    }
}

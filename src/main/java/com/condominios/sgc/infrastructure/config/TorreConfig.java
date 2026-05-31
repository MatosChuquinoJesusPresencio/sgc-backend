package com.condominios.sgc.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.condominios.sgc.application.impl.ActualizarTorreUseCaseImpl;
import com.condominios.sgc.application.impl.CrearTorreUseCaseImpl;
import com.condominios.sgc.application.impl.EliminarTorreUseCaseImpl;
import com.condominios.sgc.application.impl.ListarTorresPorCondominioUseCaseImpl;
import com.condominios.sgc.application.impl.ObtenerTorreUseCaseImpl;
import com.condominios.sgc.application.usecase.ActualizarTorreUseCase;
import com.condominios.sgc.application.usecase.CrearTorreUseCase;
import com.condominios.sgc.application.usecase.EliminarTorreUseCase;
import com.condominios.sgc.application.usecase.ListarTorresPorCondominioUseCase;
import com.condominios.sgc.application.usecase.ObtenerTorreUseCase;
import com.condominios.sgc.domain.port.CondominioPort;
import com.condominios.sgc.domain.port.TorrePort;

@Configuration
public class TorreConfig {

    @Bean
    public CrearTorreUseCase crearTorreUseCase(TorrePort torrePort, CondominioPort condominioPort) {
        return new CrearTorreUseCaseImpl(torrePort, condominioPort);
    }

    @Bean
    public ObtenerTorreUseCase obtenerTorreUseCase(TorrePort torrePort) {
        return new ObtenerTorreUseCaseImpl(torrePort);
    }

    @Bean
    public ActualizarTorreUseCase actualizarTorreUseCase(TorrePort torrePort) {
        return new ActualizarTorreUseCaseImpl(torrePort);
    }

    @Bean
    public EliminarTorreUseCase eliminarTorreUseCase(TorrePort torrePort) {
        return new EliminarTorreUseCaseImpl(torrePort);
    }

    @Bean
    public ListarTorresPorCondominioUseCase listarTorresPorCondominioUseCase(TorrePort torrePort, CondominioPort condominioPort) {
        return new ListarTorresPorCondominioUseCaseImpl(torrePort, condominioPort);
    }
}

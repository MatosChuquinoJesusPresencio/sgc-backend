package com.condominios.sgc.infrastructure.config;

import com.condominios.sgc.application.impl.logprestamocarrito.*;
import com.condominios.sgc.application.usecase.logprestamocarrito.*;
import com.condominios.sgc.domain.port.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogPrestamoCarritoConfig {

    @Bean
    public CrearLogPrestamoCarritoUseCase crearLogPrestamoCarritoUseCase(LogPrestamoCarritoPort logPrestamoCarritoPort, ConfiguracionPort configuracionPort) {
        return new CrearLogPrestamoCarritoUseCaseImpl(logPrestamoCarritoPort, configuracionPort);
    }

    @Bean
    public ListarLogsPrestamoCarritoUseCase listarLogsPrestamoCarritoUseCase(LogPrestamoCarritoPort logPrestamoCarritoPort) {
        return new ListarLogsPrestamoCarritoUseCaseImpl(logPrestamoCarritoPort);
    }

    @Bean
    public ObtenerLogPrestamoCarritoPorIdUseCase obtenerLogPrestamoCarritoPorIdUseCase(LogPrestamoCarritoPort logPrestamoCarritoPort) {
        return new ObtenerLogPrestamoCarritoPorIdUseCaseImpl(logPrestamoCarritoPort);
    }

    @Bean
    public RegistrarDevolucionLogPrestamoCarritoUseCase registrarDevolucionLogPrestamoCarritoUseCase(LogPrestamoCarritoPort logPrestamoCarritoPort) {
        return new RegistrarDevolucionLogPrestamoCarritoUseCaseImpl(logPrestamoCarritoPort);
    }

}

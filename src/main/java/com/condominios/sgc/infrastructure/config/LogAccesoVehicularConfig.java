package com.condominios.sgc.infrastructure.config;

import com.condominios.sgc.application.impl.logaccesovehicular.*;
import com.condominios.sgc.application.usecase.logaccesovehicular.*;
import com.condominios.sgc.domain.port.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogAccesoVehicularConfig {

    @Bean
    public CrearLogAccesoVehicularUseCase crearLogAccesoVehicularUseCase(LogAccesoVehicularPort logAccesoVehicularPort) {
        return new CrearLogAccesoVehicularUseCaseImpl(logAccesoVehicularPort);
    }

    @Bean
    public ListarLogsAccesoVehicularUseCase listarLogsAccesoVehicularUseCase(LogAccesoVehicularPort logAccesoVehicularPort) {
        return new ListarLogsAccesoVehicularUseCaseImpl(logAccesoVehicularPort);
    }

    @Bean
    public ObtenerLogAccesoVehicularPorIdUseCase obtenerLogAccesoVehicularPorIdUseCase(LogAccesoVehicularPort logAccesoVehicularPort) {
        return new ObtenerLogAccesoVehicularPorIdUseCaseImpl(logAccesoVehicularPort);
    }

    @Bean
    public RegistrarSalidaLogAccesoVehicularUseCase registrarSalidaLogAccesoVehicularUseCase(LogAccesoVehicularPort logAccesoVehicularPort) {
        return new RegistrarSalidaLogAccesoVehicularUseCaseImpl(logAccesoVehicularPort);
    }

}

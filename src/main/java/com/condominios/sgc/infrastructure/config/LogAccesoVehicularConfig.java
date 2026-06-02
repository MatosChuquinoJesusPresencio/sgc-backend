package com.condominios.sgc.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.condominios.sgc.application.impl.ListarLogsAccesoPorApartamentoUseCaseImpl;
import com.condominios.sgc.application.impl.ListarLogsAccesoPorCondominioUseCaseImpl;
import com.condominios.sgc.application.impl.ObtenerLogAccesoUseCaseImpl;
import com.condominios.sgc.application.impl.RegistrarEntradaVehiculoUseCaseImpl;
import com.condominios.sgc.application.impl.RegistrarSalidaVehiculoUseCaseImpl;
import com.condominios.sgc.application.usecase.ListarLogsAccesoPorApartamentoUseCase;
import com.condominios.sgc.application.usecase.ListarLogsAccesoPorCondominioUseCase;
import com.condominios.sgc.application.usecase.ObtenerLogAccesoUseCase;
import com.condominios.sgc.application.usecase.RegistrarEntradaVehiculoUseCase;
import com.condominios.sgc.application.usecase.RegistrarSalidaVehiculoUseCase;
import com.condominios.sgc.domain.port.LogAccesoVehicularPort;

@Configuration
public class LogAccesoVehicularConfig {

    @Bean
    public RegistrarEntradaVehiculoUseCase registrarEntradaVehiculoUseCase(LogAccesoVehicularPort logAccesoVehicularPort) {
        return new RegistrarEntradaVehiculoUseCaseImpl(logAccesoVehicularPort);
    }

    @Bean
    public RegistrarSalidaVehiculoUseCase registrarSalidaVehiculoUseCase(LogAccesoVehicularPort logAccesoVehicularPort) {
        return new RegistrarSalidaVehiculoUseCaseImpl(logAccesoVehicularPort);
    }

    @Bean
    public ObtenerLogAccesoUseCase obtenerLogAccesoUseCase(LogAccesoVehicularPort logAccesoVehicularPort) {
        return new ObtenerLogAccesoUseCaseImpl(logAccesoVehicularPort);
    }

    @Bean
    public ListarLogsAccesoPorCondominioUseCase listarLogsAccesoPorCondominioUseCase(LogAccesoVehicularPort logAccesoVehicularPort) {
        return new ListarLogsAccesoPorCondominioUseCaseImpl(logAccesoVehicularPort);
    }

    @Bean
    public ListarLogsAccesoPorApartamentoUseCase listarLogsAccesoPorApartamentoUseCase(LogAccesoVehicularPort logAccesoVehicularPort) {
        return new ListarLogsAccesoPorApartamentoUseCaseImpl(logAccesoVehicularPort);
    }
}

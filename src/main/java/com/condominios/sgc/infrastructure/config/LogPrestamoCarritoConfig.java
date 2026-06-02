package com.condominios.sgc.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.condominios.sgc.application.impl.FinalizarPrestamoCarritoUseCaseImpl;
import com.condominios.sgc.application.impl.IniciarPrestamoCarritoUseCaseImpl;
import com.condominios.sgc.application.impl.ListarLogsPrestamoPorApartamentoUseCaseImpl;
import com.condominios.sgc.application.impl.ListarLogsPrestamoPorCondominioUseCaseImpl;
import com.condominios.sgc.application.impl.ObtenerLogPrestamoUseCaseImpl;
import com.condominios.sgc.application.usecase.FinalizarPrestamoCarritoUseCase;
import com.condominios.sgc.application.usecase.IniciarPrestamoCarritoUseCase;
import com.condominios.sgc.application.usecase.ListarLogsPrestamoPorApartamentoUseCase;
import com.condominios.sgc.application.usecase.ListarLogsPrestamoPorCondominioUseCase;
import com.condominios.sgc.application.usecase.ObtenerLogPrestamoUseCase;
import com.condominios.sgc.domain.port.LogPrestamoCarritoPort;

@Configuration
public class LogPrestamoCarritoConfig {

    @Bean
    public IniciarPrestamoCarritoUseCase iniciarPrestamoCarritoUseCase(LogPrestamoCarritoPort logPrestamoCarritoPort) {
        return new IniciarPrestamoCarritoUseCaseImpl(logPrestamoCarritoPort);
    }

    @Bean
    public FinalizarPrestamoCarritoUseCase finalizarPrestamoCarritoUseCase(LogPrestamoCarritoPort logPrestamoCarritoPort) {
        return new FinalizarPrestamoCarritoUseCaseImpl(logPrestamoCarritoPort);
    }

    @Bean
    public ObtenerLogPrestamoUseCase obtenerLogPrestamoUseCase(LogPrestamoCarritoPort logPrestamoCarritoPort) {
        return new ObtenerLogPrestamoUseCaseImpl(logPrestamoCarritoPort);
    }

    @Bean
    public ListarLogsPrestamoPorCondominioUseCase listarLogsPrestamoPorCondominioUseCase(LogPrestamoCarritoPort logPrestamoCarritoPort) {
        return new ListarLogsPrestamoPorCondominioUseCaseImpl(logPrestamoCarritoPort);
    }

    @Bean
    public ListarLogsPrestamoPorApartamentoUseCase listarLogsPrestamoPorApartamentoUseCase(LogPrestamoCarritoPort logPrestamoCarritoPort) {
        return new ListarLogsPrestamoPorApartamentoUseCaseImpl(logPrestamoCarritoPort);
    }
}

package com.condominios.sgc.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.condominios.sgc.application.impl.ActualizarInquilinoUseCaseImpl;
import com.condominios.sgc.application.impl.CrearInquilinoUseCaseImpl;
import com.condominios.sgc.application.impl.EliminarInquilinoUseCaseImpl;
import com.condominios.sgc.application.impl.ListarInquilinosPorApartamentoUseCaseImpl;
import com.condominios.sgc.application.impl.ObtenerInquilinoUseCaseImpl;
import com.condominios.sgc.application.usecase.ActualizarInquilinoUseCase;
import com.condominios.sgc.application.usecase.CrearInquilinoUseCase;
import com.condominios.sgc.application.usecase.EliminarInquilinoUseCase;
import com.condominios.sgc.application.usecase.ListarInquilinosPorApartamentoUseCase;
import com.condominios.sgc.application.usecase.ObtenerInquilinoUseCase;
import com.condominios.sgc.domain.port.ConfiguracionPort;
import com.condominios.sgc.domain.port.InquilinoPort;

@Configuration
public class InquilinoConfig {

    @Bean
    public CrearInquilinoUseCase crearInquilinoUseCase(InquilinoPort inquilinoPort, ConfiguracionPort configuracionPort) {
        return new CrearInquilinoUseCaseImpl(inquilinoPort, configuracionPort);
    }

    @Bean
    public ObtenerInquilinoUseCase obtenerInquilinoUseCase(InquilinoPort inquilinoPort) {
        return new ObtenerInquilinoUseCaseImpl(inquilinoPort);
    }

    @Bean
    public ActualizarInquilinoUseCase actualizarInquilinoUseCase(InquilinoPort inquilinoPort) {
        return new ActualizarInquilinoUseCaseImpl(inquilinoPort);
    }

    @Bean
    public EliminarInquilinoUseCase eliminarInquilinoUseCase(InquilinoPort inquilinoPort) {
        return new EliminarInquilinoUseCaseImpl(inquilinoPort);
    }

    @Bean
    public ListarInquilinosPorApartamentoUseCase listarInquilinosPorApartamentoUseCase(InquilinoPort inquilinoPort) {
        return new ListarInquilinosPorApartamentoUseCaseImpl(inquilinoPort);
    }
}

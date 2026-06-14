package com.condominios.sgc.infrastructure.config;

import com.condominios.sgc.application.impl.inquilino.*;
import com.condominios.sgc.application.usecase.inquilino.*;
import com.condominios.sgc.domain.port.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InquilinoConfig {

    @Bean
    public ActualizarInquilinoPorIdUseCase actualizarInquilinoPorIdUseCase(InquilinoPort inquilinoPort) {
        return new ActualizarInquilinoPorIdUseCaseImpl(inquilinoPort);
    }

    @Bean
    public CrearInquilinoUseCase crearInquilinoUseCase(InquilinoPort inquilinoPort, ConfiguracionPort configuracionPort) {
        return new CrearInquilinoUseCaseImpl(inquilinoPort, configuracionPort);
    }

    @Bean
    public EliminarInquilinoPorIdUseCase eliminarInquilinoPorIdUseCase(InquilinoPort inquilinoPort) {
        return new EliminarInquilinoPorIdUseCaseImpl(inquilinoPort);
    }

    @Bean
    public ListarInquilinosUseCase listarInquilinosUseCase(InquilinoPort inquilinoPort) {
        return new ListarInquilinosUseCaseImpl(inquilinoPort);
    }

    @Bean
    public ObtenerInquilinoPorDocumentoUseCase obtenerInquilinoPorDocumentoUseCase(InquilinoPort inquilinoPort) {
        return new ObtenerInquilinoPorDocumentoUseCaseImpl(inquilinoPort);
    }

    @Bean
    public ObtenerInquilinoPorIdUseCase obtenerInquilinoPorIdUseCase(InquilinoPort inquilinoPort) {
        return new ObtenerInquilinoPorIdUseCaseImpl(inquilinoPort);
    }

}

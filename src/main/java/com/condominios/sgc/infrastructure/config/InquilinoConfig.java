package com.condominios.sgc.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.condominios.sgc.application.impl.InquilinoUseCaseImpl;
import com.condominios.sgc.application.usecase.AsignarVehiculoAInquilinoUseCase;
import com.condominios.sgc.application.usecase.CrearInquilinoUseCase;
import com.condominios.sgc.application.usecase.EliminarInquilinoUseCase;
import com.condominios.sgc.application.usecase.ListarInquilinosPorApartamentoUseCase;
import com.condominios.sgc.application.usecase.ObtenerInquilinoUseCase;
import com.condominios.sgc.application.usecase.RemoverVehiculoDeInquilinoUseCase;
import com.condominios.sgc.domain.port.ConfiguracionPort;
import com.condominios.sgc.domain.port.InquilinoPort;

@Configuration
public class InquilinoConfig {

    @Bean
    public InquilinoUseCaseImpl inquilinoUseCaseImpl(InquilinoPort inquilinoPort, ConfiguracionPort configuracionPort) {
        return new InquilinoUseCaseImpl(inquilinoPort, configuracionPort);
    }

    @Bean
    public CrearInquilinoUseCase crearInquilinoUseCase(InquilinoUseCaseImpl impl) {
        return impl;
    }

    @Bean
    public ObtenerInquilinoUseCase obtenerInquilinoUseCase(InquilinoUseCaseImpl impl) {
        return impl;
    }

    @Bean
    public ListarInquilinosPorApartamentoUseCase listarInquilinosPorApartamentoUseCase(InquilinoUseCaseImpl impl) {
        return impl;
    }

    @Bean
    public AsignarVehiculoAInquilinoUseCase asignarVehiculoAInquilinoUseCase(InquilinoUseCaseImpl impl) {
        return impl;
    }

    @Bean
    public RemoverVehiculoDeInquilinoUseCase removerVehiculoDeInquilinoUseCase(InquilinoUseCaseImpl impl) {
        return impl;
    }

    @Bean
    public EliminarInquilinoUseCase eliminarInquilinoUseCase(InquilinoUseCaseImpl impl) {
        return impl;
    }
}

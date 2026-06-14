package com.condominios.sgc.infrastructure.config;

import com.condominios.sgc.application.impl.token.*;
import com.condominios.sgc.application.usecase.token.*;
import com.condominios.sgc.domain.port.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TokenConfig {

    @Bean
    public CrearTokenUseCase crearTokenUseCase(TokenPort tokenPort) {
        return new CrearTokenUseCaseImpl(tokenPort);
    }

    @Bean
    public EliminarTokenPorIdUseCase eliminarTokenPorIdUseCase(TokenPort tokenPort) {
        return new EliminarTokenPorIdUseCaseImpl(tokenPort);
    }

    @Bean
    public ListarTokensUseCase listarTokensUseCase(TokenPort tokenPort) {
        return new ListarTokensUseCaseImpl(tokenPort);
    }

    @Bean
    public ObtenerTokenPorIdUseCase obtenerTokenPorIdUseCase(TokenPort tokenPort) {
        return new ObtenerTokenPorIdUseCaseImpl(tokenPort);
    }

    @Bean
    public ObtenerTokenPorTokenUseCase obtenerTokenPorTokenUseCase(TokenPort tokenPort) {
        return new ObtenerTokenPorTokenUseCaseImpl(tokenPort);
    }

    @Bean
    public UsarTokenUseCase usarTokenUseCase(TokenPort tokenPort) {
        return new UsarTokenUseCaseImpl(tokenPort);
    }

}

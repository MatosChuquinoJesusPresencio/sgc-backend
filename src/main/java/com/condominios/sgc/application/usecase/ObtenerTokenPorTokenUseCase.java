package com.condominios.sgc.application.usecase;

import com.condominios.sgc.application.dto.response.TokenResponse;

public interface ObtenerTokenPorTokenUseCase {
    TokenResponse ejecutar(String token);
}

package com.condominios.sgc.application.usecase.token;

import com.condominios.sgc.application.dto.response.TokenResponse;

public interface ObtenerTokenPorTokenUseCase {
    TokenResponse ejecutar(String token);
}

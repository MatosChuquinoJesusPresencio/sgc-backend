package com.condominios.sgc.application.usecase;

import java.util.List;

import com.condominios.sgc.application.dto.response.TokenResponse;

public interface ListarTokensUseCase {
    List<TokenResponse> ejecutar();
}

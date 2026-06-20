package com.condominios.sgc.application.port.out;

import java.util.Optional;

import com.condominios.sgc.domain.model.TokenModel;

public interface TokenRepositoryPort {
    Optional<TokenModel> obtenerPorToken(String token);
    TokenModel guardar(TokenModel modelo);
    void eliminar(TokenModel modelo);
}

package com.condominios.sgc.application.port.out;

import com.condominios.sgc.domain.model.TokenModel;

import java.util.List;
import java.util.Optional;

public interface TokenRepositoryPort {
    TokenModel guardar(TokenModel token);
    Optional<TokenModel> buscarPorId(Long id);
    Optional<TokenModel> buscarPorToken(String token);
    List<TokenModel> listarPorUsuario(Long idUsuario);
}

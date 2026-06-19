package com.condominios.sgc.application.port.out.model;

import com.condominios.sgc.domain.model.TokenModel;

import java.util.List;
import java.util.Optional;

public interface TokenRepository {
    Optional<TokenModel> buscarPorId(Long id);
    Optional<TokenModel> buscarPorToken(String token);
    List<TokenModel> listarPorUsuario(Long idUsuario);
    TokenModel guardar(TokenModel token);
    void eliminar(Long id);
}

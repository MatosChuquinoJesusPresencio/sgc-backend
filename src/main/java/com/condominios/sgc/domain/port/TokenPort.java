package com.condominios.sgc.domain.port;

import com.condominios.sgc.domain.model.TokenModel;
import java.util.List;
import java.util.Optional;

public interface TokenPort {
    TokenModel guardar(TokenModel token);
    Optional<TokenModel> obtenerPorId(Long id);
    Optional<TokenModel> obtenerPorToken(String token);
    List<TokenModel> obtenerTodos();
    List<TokenModel> obtenerPorUsuario(Long idUsuario);
    List<TokenModel> obtenerNoUsados();
    void eliminarPorId(Long id);
}


package com.condominios.sgc.domain.port;

import com.condominios.sgc.domain.dto.request.PaginacionRequest;
import com.condominios.sgc.domain.dto.response.PaginacionResponse;
import com.condominios.sgc.domain.filter.TokenFilter;
import com.condominios.sgc.domain.model.TokenModel;
import java.util.List;
import java.util.Optional;

public interface TokenPort {
    TokenModel guardar(TokenModel token);
    Optional<TokenModel> obtenerPorId(Long id);
    Optional<TokenModel> obtenerPorToken(String token);
    List<TokenModel> obtenerTodos();
    PaginacionResponse<TokenModel> obtenerTodos(PaginacionRequest paginacion, TokenFilter Filter);
    List<TokenModel> obtenerPorUsuario(Long idUsuario);
    List<TokenModel> obtenerNoUsados();
    void eliminarPorId(Long id);
}


package com.condominios.sgc.application.dto.response;

import java.time.Instant;

import com.condominios.sgc.domain.auxiliar.TipoToken;
import com.condominios.sgc.domain.model.TokenModel;

public record TokenResponse(
    Long id,
    TipoToken tipo,
    String token,
    Instant expiracion,
    Boolean usado,
    Long idUsuario
) {
    public static TokenResponse desdeModelo(TokenModel model) {
        return new TokenResponse(
            model.getId(), model.getTipo(), model.getToken(),
            model.getExpiracion(), model.getUsado(), model.getIdUsuario());
    }
}

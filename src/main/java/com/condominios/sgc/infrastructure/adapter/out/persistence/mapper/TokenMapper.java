package com.condominios.sgc.infrastructure.adapter.out.persistence.mapper;

import com.condominios.sgc.domain.model.TokenModel;
import com.condominios.sgc.domain.type.TipoToken;
import com.condominios.sgc.infrastructure.adapter.out.persistence.entity.TokenEntity;

public final class TokenMapper {

    private TokenMapper() {}

    public static TokenModel toModel(TokenEntity e) {
        if (e == null) return null;
        return new TokenModel(
            e.getId(),
            TipoToken.valueOf(e.getTipo()),
            e.getToken(),
            e.getExpiracion(),
            e.getIdUsuario()
        );
    }

    public static TokenEntity toEntity(TokenModel m) {
        if (m == null) return null;
        TokenEntity e = new TokenEntity();
        e.setId(m.getId());
        e.setTipo(m.getTipo().name());
        e.setToken(m.getToken());
        e.setExpiracion(m.getExpiracion());
        e.setIdUsuario(m.getIdUsuario());
        return e;
    }
}

package com.condominios.sgc.infrastructure.persistence.mapper;

import com.condominios.sgc.domain.model.TokenModel;
import com.condominios.sgc.infrastructure.persistence.entity.TokenEntity;

public class TokenMapper {

    public TokenModel aDominio(TokenEntity entidad) {
        return new TokenModel(
            entidad.getId(),
            entidad.getTipo(),
            entidad.getToken(),
            entidad.getExpiracion(),
            entidad.getUsado(),
            entidad.getIdUsuario()
        );
    }

    public TokenEntity aEntidad(TokenModel dominio) {
        TokenEntity entidad = new TokenEntity();
        entidad.setId(dominio.getId());
        entidad.setTipo(dominio.getTipo());
        entidad.setToken(dominio.getToken());
        entidad.setExpiracion(dominio.getExpiracion());
        entidad.setUsado(dominio.getUsado());
        entidad.setIdUsuario(dominio.getIdUsuario());
        return entidad;
    }
}

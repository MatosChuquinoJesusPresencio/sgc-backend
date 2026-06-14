package com.condominios.sgc.infrastructure.persistence.mapper;

import com.condominios.sgc.domain.model.TokenModel;
import com.condominios.sgc.infrastructure.persistence.entity.TokenEntity;
import com.condominios.sgc.infrastructure.persistence.entity.UsuarioEntity;
import org.springframework.stereotype.Component;

@Component
public class TokenMapper {

    public TokenModel aModelo(TokenEntity entidad) {
        return new TokenModel(
            entidad.getId(), entidad.getTipo(), entidad.getToken(),
            entidad.getExpiracion(), entidad.getUsado(), entidad.getIdUsuario());
    }

    public TokenEntity aEntidad(TokenModel modelo) {
        TokenEntity entidad = new TokenEntity();
        entidad.setId(modelo.getId());
        entidad.setTipo(modelo.getTipo());
        entidad.setToken(modelo.getToken());
        entidad.setExpiracion(modelo.getExpiracion());
        entidad.setUsado(modelo.getUsado());
        if (modelo.getIdUsuario() != null) {
            UsuarioEntity referencia = new UsuarioEntity();
            referencia.setId(modelo.getIdUsuario());
            entidad.setUsuario(referencia);
        }
        return entidad;
    }
}

package com.condominios.sgc.infrastructure.persistence.mapper;

import com.condominios.sgc.domain.model.RestablecimientoTokenModel;
import com.condominios.sgc.infrastructure.persistence.entity.RestablecimientoTokenEntity;
import com.condominios.sgc.infrastructure.persistence.entity.UsuarioEntity;

import static com.condominios.sgc.domain.util.EntidadUtil.idDe;

public final class RestablecimientoTokenMapper {

    private RestablecimientoTokenMapper() {}

    public static RestablecimientoTokenEntity toEntity(RestablecimientoTokenModel model, UsuarioEntity usuario) {
        if (model == null) return null;
        var e = new RestablecimientoTokenEntity();
        e.setId(model.getId());
        e.setUsuario(usuario);
        e.setToken(model.getToken());
        e.setExpiracion(model.getExpiracion());
        e.setUsado(model.isUsado());
        return e;
    }

    public static RestablecimientoTokenModel toModel(RestablecimientoTokenEntity e) {
        if (e == null) return null;
        return new RestablecimientoTokenModel(
                e.getId(),
                idDe(e.getUsuario(), UsuarioEntity::getId),
                e.getToken(),
                e.getExpiracion()
        );
    }
}

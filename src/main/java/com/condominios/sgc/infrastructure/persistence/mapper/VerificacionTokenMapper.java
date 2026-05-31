package com.condominios.sgc.infrastructure.persistence.mapper;

import com.condominios.sgc.domain.model.VerificacionTokenModel;
import com.condominios.sgc.infrastructure.persistence.entity.UsuarioEntity;
import com.condominios.sgc.infrastructure.persistence.entity.VerificacionTokenEntity;

import static com.condominios.sgc.domain.util.EntidadUtil.idDe;

public final class VerificacionTokenMapper {

    private VerificacionTokenMapper() {}

    public static VerificacionTokenEntity toEntity(VerificacionTokenModel model, UsuarioEntity usuario) {
        if (model == null) return null;
        var e = new VerificacionTokenEntity();
        e.setId(model.getId());
        e.setUsuario(usuario);
        e.setNuevoCorreo(model.getNuevoCorreo());
        e.setToken(model.getToken());
        e.setExpiracion(model.getExpiracion());
        e.setUsado(model.isUsado());
        return e;
    }

    public static VerificacionTokenModel toModel(VerificacionTokenEntity e) {
        if (e == null) return null;
        return new VerificacionTokenModel(
                e.getId(),
                idDe(e.getUsuario(), UsuarioEntity::getId),
                e.getNuevoCorreo(),
                e.getToken(),
                e.getExpiracion()
        );
    }
}

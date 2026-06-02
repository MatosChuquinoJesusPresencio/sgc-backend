package com.condominios.sgc.infrastructure.persistence.mapper;

import static com.condominios.sgc.domain.util.EntidadUtil.idDe;

import com.condominios.sgc.domain.model.UsuarioModel;
import com.condominios.sgc.infrastructure.persistence.entity.CondominioEntity;
import com.condominios.sgc.infrastructure.persistence.entity.UsuarioEntity;

public final class UsuarioMapper {

    private UsuarioMapper() {}

    public static UsuarioEntity toEntity(UsuarioModel model) {
        if (model == null) return null;
        UsuarioEntity e = new UsuarioEntity();
        e.setId(model.getId());
        e.setNombres(model.getNombres());
        e.setApellidos(model.getApellidos());
        e.setCorreo(model.getCorreo());
        e.setTelefono(model.getTelefono());
        e.setRol(model.getRol());
        e.setActivo(model.isActivo());
        e.setCorreoPendiente(model.getCorreoPendiente());
        e.setCorreoVerificado(model.isCorreoVerificado());
        e.setPasswordHash(model.getContrasena());
        return e;
    }

    public static UsuarioModel toModel(UsuarioEntity e) {
        if (e == null) return null;
        return new UsuarioModel(
                e.getId(),
                e.getNombres(),
                e.getApellidos(),
                e.getCorreo(),
                e.getTelefono(),
                e.getRol(),
                e.getActivo(),
                idDe(e.getCondominio(), CondominioEntity::getId),
                e.getCorreoPendiente(),
                e.getCorreoVerificado(),
                e.getPasswordHash()
        );
    }
}

package com.condominios.sgc.persistence.mapper;

import com.condominios.sgc.domain.model.UsuarioModel;
import com.condominios.sgc.persistence.entity.UsuarioEntity;

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
        return e;
    }

    public static UsuarioModel toModel(UsuarioEntity e) {
        if (e == null) return null;
        UsuarioModel m = new UsuarioModel(
                e.getId(), e.getNombres(), e.getApellidos(), e.getCorreo(),
                e.getTelefono(), e.getRol(),
                e.getCondominio() != null ? e.getCondominio().getId() : null);
        if (!e.getActivo()) {
            m.desactivar();
        }
        if (e.getApartamento() != null) {
            m.asignarApartamento(e.getApartamento().getId());
        }
        return m;
    }
}

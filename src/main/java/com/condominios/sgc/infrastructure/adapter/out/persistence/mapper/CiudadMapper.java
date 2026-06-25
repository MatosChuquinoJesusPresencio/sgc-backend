package com.condominios.sgc.infrastructure.adapter.out.persistence.mapper;

import com.condominios.sgc.domain.model.CiudadModel;
import com.condominios.sgc.infrastructure.adapter.out.persistence.entity.CiudadEntity;

public final class CiudadMapper {

    private CiudadMapper() {}

    public static CiudadModel toModel(CiudadEntity e) {
        if (e == null) return null;
        return new CiudadModel(e.getId(), e.getNombre(), e.getIdPais());
    }

    public static CiudadEntity toEntity(CiudadModel c) {
        if (c == null) return null;
        CiudadEntity e = new CiudadEntity();
        e.setId(c.id());
        e.setNombre(c.nombre());
        e.setIdPais(c.idPais());
        return e;
    }
}

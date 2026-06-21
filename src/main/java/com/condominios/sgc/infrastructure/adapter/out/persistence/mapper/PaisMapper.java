package com.condominios.sgc.infrastructure.adapter.out.persistence.mapper;

import com.condominios.sgc.domain.model.catalog.PaisModel;
import com.condominios.sgc.infrastructure.adapter.out.persistence.entity.catalog.PaisEntity;

public final class PaisMapper {

    private PaisMapper() {}

    public static PaisModel toModel(PaisEntity e) {
        if (e == null) return null;
        return new PaisModel(e.getId(), e.getNombre(), e.getCodigoIso(), e.getIdMoneda());
    }

    public static PaisEntity toEntity(PaisModel p) {
        if (p == null) return null;
        PaisEntity e = new PaisEntity();
        e.setId(p.id());
        e.setNombre(p.nombre());
        e.setCodigoIso(p.codigoIso());
        e.setIdMoneda(p.idMoneda());
        return e;
    }
}

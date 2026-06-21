package com.condominios.sgc.infrastructure.adapter.out.persistence.mapper;

import com.condominios.sgc.domain.model.MonedaModel;
import com.condominios.sgc.infrastructure.adapter.out.persistence.entity.MonedaEntity;

public final class MonedaMapper {

    private MonedaMapper() {}

    public static MonedaModel toModel(MonedaEntity e) {
        if (e == null) return null;
        return new MonedaModel(e.getId(), e.getNombre(), e.getCodigo(), e.getSimbolo());
    }

    public static MonedaEntity toEntity(MonedaModel m) {
        if (m == null) return null;
        MonedaEntity e = new MonedaEntity();
        e.setId(m.id());
        e.setNombre(m.nombre());
        e.setCodigo(m.codigo());
        e.setSimbolo(m.simbolo());
        return e;
    }
}

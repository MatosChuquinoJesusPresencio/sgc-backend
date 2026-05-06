package com.condominios.sgc.persistence.mapper;

import com.condominios.sgc.domain.model.TorreModel;
import com.condominios.sgc.persistence.entity.TorreEntity;
import java.util.ArrayList;

public final class TorreMapper {

    private TorreMapper() {}

    public static TorreEntity toEntity(TorreModel model) {
        if (model == null) return null;
        TorreEntity e = new TorreEntity();
        e.setId(model.getId());
        e.setNombre(model.getNombre());
        return e;
    }

    public static TorreModel toModel(TorreEntity e) {
        if (e == null) return null;
        return new TorreModel(e.getId(), e.getNombre(), new ArrayList<>());
    }
}

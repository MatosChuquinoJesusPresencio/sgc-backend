package com.condominios.sgc.infrastructure.persistence.mapper;

import static com.condominios.sgc.domain.util.EntidadUtil.idDe;

import com.condominios.sgc.domain.model.TorreModel;
import com.condominios.sgc.infrastructure.persistence.entity.CondominioEntity;
import com.condominios.sgc.infrastructure.persistence.entity.TorreEntity;

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
        return new TorreModel(
            e.getId(), 
            e.getNombre(),
            idDe(e.getCondominio(), CondominioEntity::getId)
        );
    }
}

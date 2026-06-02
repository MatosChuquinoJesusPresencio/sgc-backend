package com.condominios.sgc.infrastructure.persistence.mapper;

import static com.condominios.sgc.domain.util.EntidadUtil.idDe;

import com.condominios.sgc.domain.model.PisoModel;
import com.condominios.sgc.infrastructure.persistence.entity.PisoEntity;
import com.condominios.sgc.infrastructure.persistence.entity.TorreEntity;

public final class PisoMapper {

    private PisoMapper() {}

    public static PisoEntity toEntity(PisoModel model) {
        if (model == null) return null;
        PisoEntity e = new PisoEntity();
        e.setId(model.getId());
        e.setNumero(model.getNumero());
        return e;
    }

    public static PisoModel toModel(PisoEntity e) {
        if (e == null) return null;
        return new PisoModel(
            e.getId(), 
            e.getNumero(),
            idDe(e.getTorre(), TorreEntity::getId)
        );
    }
}

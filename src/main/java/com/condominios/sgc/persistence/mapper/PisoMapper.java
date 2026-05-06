package com.condominios.sgc.persistence.mapper;

import com.condominios.sgc.domain.model.PisoModel;
import com.condominios.sgc.persistence.entity.PisoEntity;
import java.util.ArrayList;

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
        return new PisoModel(e.getId(), e.getNumero(), new ArrayList<>());
    }
}

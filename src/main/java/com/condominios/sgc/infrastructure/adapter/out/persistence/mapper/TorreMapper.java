package com.condominios.sgc.infrastructure.adapter.out.persistence.mapper;

import com.condominios.sgc.domain.model.PisoModel;
import com.condominios.sgc.domain.model.TorreModel;
import com.condominios.sgc.infrastructure.adapter.out.persistence.entity.PisoEntity;
import com.condominios.sgc.infrastructure.adapter.out.persistence.entity.TorreEntity;
import java.util.List;

public final class TorreMapper {

    private TorreMapper() {}

    public static TorreModel toModel(TorreEntity e) {
        if (e == null) return null;
        List<PisoModel> pisos = e.getPisos().stream()
            .map(PisoMapper::toModel)
            .toList();
        return new TorreModel(e.getId(), e.getNombre(), pisos);
    }

    public static TorreEntity toEntity(TorreModel m) {
        if (m == null) return null;
        TorreEntity e = new TorreEntity();
        e.setId(m.getId());
        e.setNombre(m.getNombre());
        if (m.getPisos() != null) {
            List<PisoEntity> pisos = m.getPisos().stream()
                .map(PisoMapper::toEntity)
                .toList();
            pisos.forEach(p -> p.setTorre(e));
            e.setPisos(pisos);
        }
        return e;
    }

    public static void applyToEntity(TorreModel m, TorreEntity e) {
        e.setNombre(m.getNombre());
    }
}

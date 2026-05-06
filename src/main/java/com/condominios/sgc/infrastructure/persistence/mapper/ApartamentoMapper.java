package com.condominios.sgc.infrastructure.persistence.mapper;

import com.condominios.sgc.domain.model.ApartamentoModel;
import com.condominios.sgc.infrastructure.persistence.entity.ApartamentoEntity;

public final class ApartamentoMapper {

    private ApartamentoMapper() {}

    public static ApartamentoEntity toEntity(ApartamentoModel model) {
        if (model == null) return null;
        ApartamentoEntity e = new ApartamentoEntity();
        e.setId(model.getId());
        e.setNumero(model.getNumero());
        e.setDerechoEstacionamiento(model.getDerechoEstacionamiento());
        e.setMetraje(model.getMetraje());
        return e;
    }

    public static ApartamentoModel toModel(ApartamentoEntity e) {
        if (e == null) return null;
        ApartamentoModel m = new ApartamentoModel(
                e.getId(), e.getNumero(), e.getDerechoEstacionamiento(),
                e.getMetraje(),
                e.getPiso() != null ? e.getPiso().getId() : null);
        if (e.getPropietario() != null) {
            m.asignarPropietario(e.getPropietario().getId());
        }
        return m;
    }
}

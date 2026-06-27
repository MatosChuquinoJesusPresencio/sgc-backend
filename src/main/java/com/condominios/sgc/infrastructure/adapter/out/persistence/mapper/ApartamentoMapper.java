package com.condominios.sgc.infrastructure.adapter.out.persistence.mapper;

import com.condominios.sgc.domain.model.ApartamentoModel;
import com.condominios.sgc.infrastructure.adapter.out.persistence.entity.ApartamentoEntity;

public final class ApartamentoMapper {

    private ApartamentoMapper() {}

    public static ApartamentoModel toModel(ApartamentoEntity e) {
        if (e == null) return null;
        return new ApartamentoModel(
            e.getId(),
            e.getNumero(),
            e.getDerechoEstacionamiento(),
            e.getMetraje(),
            e.getIdPropietario()
        );
    }

    public static ApartamentoEntity toEntity(ApartamentoModel m) {
        if (m == null) return null;
        ApartamentoEntity e = new ApartamentoEntity();
        e.setId(m.getId());
        e.setNumero(m.getNumero());
        e.setDerechoEstacionamiento(m.getDerechoEstacionamiento());
        e.setMetraje(m.getMetraje());
        e.setIdPropietario(m.getIdPropietario());
        return e;
    }
}

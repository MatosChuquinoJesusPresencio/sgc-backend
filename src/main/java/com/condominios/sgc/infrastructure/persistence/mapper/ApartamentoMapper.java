package com.condominios.sgc.infrastructure.persistence.mapper;

import static com.condominios.sgc.domain.util.EntidadUtil.idDe;

import com.condominios.sgc.domain.model.ApartamentoModel;
import com.condominios.sgc.infrastructure.persistence.entity.ApartamentoEntity;
import com.condominios.sgc.infrastructure.persistence.entity.PisoEntity;
import com.condominios.sgc.infrastructure.persistence.entity.UsuarioEntity;

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
        return new ApartamentoModel(
                e.getId(), 
                e.getNumero(), 
                e.getDerechoEstacionamiento(),
                e.getMetraje(), 
                idDe(e.getPropietario(), UsuarioEntity::getId),
                idDe(e.getPiso(), PisoEntity::getId)
        );
    }
}

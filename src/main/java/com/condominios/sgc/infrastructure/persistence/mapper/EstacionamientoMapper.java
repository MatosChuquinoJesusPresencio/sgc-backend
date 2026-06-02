package com.condominios.sgc.infrastructure.persistence.mapper;

import static com.condominios.sgc.domain.util.EntidadUtil.idDe;

import com.condominios.sgc.domain.model.EstacionamientoModel;
import com.condominios.sgc.infrastructure.persistence.entity.ApartamentoEntity;
import com.condominios.sgc.infrastructure.persistence.entity.CondominioEntity;
import com.condominios.sgc.infrastructure.persistence.entity.EstacionamientoEntity;

public final class EstacionamientoMapper {

    private EstacionamientoMapper() {}

    public static EstacionamientoEntity toEntity(EstacionamientoModel model) {
        if (model == null) return null;
        EstacionamientoEntity e = new EstacionamientoEntity();
        e.setId(model.getId());
        e.setNumero(model.getNumero());
        e.setTipoVehiculo(model.getTipoVehiculo());
        e.setCapacidadMaxima(model.getCapacidadMaxima());
        e.setCantidadActual(model.getCantidadActual());
        e.setDisponible(model.isDisponible());
        return e;
    }

    public static EstacionamientoModel toModel(EstacionamientoEntity e) {
        if (e == null) return null;
       return new EstacionamientoModel(
                e.getId(), 
                e.getNumero(),
                e.getTipoVehiculo(), 
                e.getCapacidadMaxima(),
                e.getCantidadActual(), 
                e.getDisponible(),
                idDe(e.getCondominio(), CondominioEntity::getId),
                idDe(e.getApartamento(), ApartamentoEntity::getId)
        );
    }
}

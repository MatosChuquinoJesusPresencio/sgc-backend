package com.condominios.sgc.infrastructure.adapter.out.persistence.mapper;

import com.condominios.sgc.domain.model.EstacionamientoModel;
import com.condominios.sgc.domain.type.TipoVehiculo;
import com.condominios.sgc.infrastructure.adapter.out.persistence.entity.EstacionamientoEntity;

public final class EstacionamientoMapper {

    private EstacionamientoMapper() {}

    public static EstacionamientoModel toModel(EstacionamientoEntity e) {
        if (e == null) return null;
        return new EstacionamientoModel(
            e.getId(),
            e.getNumero(),
            e.getTipoVehiculo() != null ? TipoVehiculo.valueOf(e.getTipoVehiculo()) : null,
            e.getCapacidadMaxima(),
            e.getCantidadActual(),
            e.getDisponible(),
            e.getIdApartamento(),
            e.getIdCondominio()
        );
    }

    public static EstacionamientoEntity toEntity(EstacionamientoModel m) {
        if (m == null) return null;
        EstacionamientoEntity e = new EstacionamientoEntity();
        e.setId(m.getId());
        e.setNumero(m.getNumero());
        e.setTipoVehiculo(m.getTipoVehiculo() != null ? m.getTipoVehiculo().name() : null);
        e.setCapacidadMaxima(m.getCapacidadMaxima());
        e.setCantidadActual(m.getCantidadActual());
        e.setDisponible(m.getDisponible());
        e.setIdApartamento(m.getIdApartamento());
        e.setIdCondominio(m.getIdCondominio());
        return e;
    }
}

package com.condominios.sgc.persistence.mapper;

import com.condominios.sgc.domain.model.EstacionamientoModel;
import com.condominios.sgc.persistence.entity.EstacionamientoEntity;

public final class EstacionamientoMapper {

    private EstacionamientoMapper() {}

    public static EstacionamientoEntity toEntity(EstacionamientoModel model) {
        if (model == null) return null;
        EstacionamientoEntity e = new EstacionamientoEntity();
        e.setId(model.getId());
        e.setNumero(model.getNumero());
        e.setCantidadVehiculosMax(model.getCantidadVehiculosMax());
        e.setTipoVehiculo(model.getTipoVehiculo());
        e.setDisponible(model.isDisponible());
        return e;
    }

    public static EstacionamientoModel toModel(EstacionamientoEntity e) {
        if (e == null) return null;
        EstacionamientoModel m = new EstacionamientoModel(
                e.getId(), e.getNumero(), e.getCantidadVehiculosMax(),
                e.getTipoVehiculo(),
                e.getCondominio() != null ? e.getCondominio().getId() : null);
        if (e.getApartamento() != null) {
            m.asignarAApartamento(e.getApartamento().getId());
        }
        if (e.getVehiculoAsignado() != null && !e.getDisponible()) {
            m.ocuparConVehiculo(e.getVehiculoAsignado().getId());
        }
        return m;
    }
}

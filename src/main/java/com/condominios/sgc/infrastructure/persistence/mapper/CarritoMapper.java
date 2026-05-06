package com.condominios.sgc.infrastructure.persistence.mapper;

import com.condominios.sgc.domain.model.CarritoModel;
import com.condominios.sgc.infrastructure.persistence.entity.CarritoEntity;

public final class CarritoMapper {

    private CarritoMapper() {}

    public static CarritoEntity toEntity(CarritoModel model) {
        if (model == null) return null;
        CarritoEntity e = new CarritoEntity();
        e.setId(model.getId());
        e.setCodigo(model.getCodigo());
        e.setEstado(model.getEstado());
        return e;
    }

    public static CarritoModel toModel(CarritoEntity e) {
        if (e == null) return null;
        return new CarritoModel(e.getId(), e.getCodigo(), e.getEstado());
    }
}

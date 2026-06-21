package com.condominios.sgc.infrastructure.adapter.out.persistence.mapper;

import com.condominios.sgc.domain.model.CarritoModel;
import com.condominios.sgc.domain.type.EstadoCarrito;
import com.condominios.sgc.infrastructure.adapter.out.persistence.entity.CarritoEntity;

public final class CarritoMapper {

    private CarritoMapper() {}

    public static CarritoModel toModel(CarritoEntity e) {
        if (e == null) return null;
        return new CarritoModel(
            e.getId(),
            e.getCodigo(),
            EstadoCarrito.valueOf(e.getEstado()),
            e.getIdCondominio()
        );
    }

    public static CarritoEntity toEntity(CarritoModel m) {
        if (m == null) return null;
        CarritoEntity e = new CarritoEntity();
        e.setId(m.getId());
        e.setCodigo(m.getCodigo());
        e.setEstado(m.getEstado().name());
        e.setIdCondominio(m.getIdCondominio());
        return e;
    }
}

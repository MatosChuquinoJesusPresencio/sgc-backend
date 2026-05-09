package com.condominios.sgc.infrastructure.persistence.mapper;

import com.condominios.sgc.domain.model.CondominioModel;
import com.condominios.sgc.infrastructure.persistence.entity.CondominioEntity;
import com.condominios.sgc.infrastructure.persistence.entity.ConfiguracionEntity;

import java.util.ArrayList;

public final class CondominioMapper {

    private CondominioMapper() {}

    public static CondominioEntity toEntity(CondominioModel model, ConfiguracionEntity configuracion) {
        if (model == null) return null;
        CondominioEntity e = new CondominioEntity();
        e.setId(model.getId());
        e.setNombre(model.getNombre());
        e.setPais(model.getPais());
        e.setCiudad(model.getCiudad());
        e.setDireccion(model.getDireccion());
        e.setFechaCreacion(model.getFechaCreacion());
        e.setConfiguracion(configuracion);
        return e;
    }

    public static CondominioEntity toEntity(CondominioModel model) {
        return toEntity(model, null);
    }

    public static CondominioModel toModel(CondominioEntity e) {
        if (e == null) return null;
        return new CondominioModel(
                e.getId(), e.getNombre(), e.getPais(), e.getCiudad(), e.getDireccion(),
                e.getFechaCreacion(),
                e.getConfiguracion() != null ? e.getConfiguracion().getId() : null,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>());
    }
}

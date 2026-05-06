package com.condominios.sgc.persistence.mapper;

import com.condominios.sgc.domain.model.CondominioModel;
import com.condominios.sgc.persistence.entity.CondominioEntity;
import java.util.ArrayList;

public final class CondominioMapper {

    private CondominioMapper() {}

    public static CondominioEntity toEntity(CondominioModel model) {
        if (model == null) return null;
        CondominioEntity e = new CondominioEntity();
        e.setId(model.getId());
        e.setNombre(model.getNombre());
        e.setPais(model.getPais());
        e.setCiudad(model.getCiudad());
        e.setDireccion(model.getDireccion());
        e.setFechaCreacion(model.getFechaCreacion());
        return e;
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

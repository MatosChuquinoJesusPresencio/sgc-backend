package com.condominios.sgc.infrastructure.persistence.mapper;

import com.condominios.sgc.domain.model.TorreModel;
import com.condominios.sgc.infrastructure.persistence.entity.TorreEntity;

public class TorreMapper {

    public TorreModel aDominio(TorreEntity entidad) {
        return new TorreModel(entidad.getId(), entidad.getNombre(), java.util.Collections.emptyList());
    }

    public TorreEntity aEntidad(TorreModel dominio) {
        TorreEntity entidad = new TorreEntity();
        entidad.setId(dominio.getId());
        entidad.setNombre(dominio.getNombre());
        return entidad;
    }
}

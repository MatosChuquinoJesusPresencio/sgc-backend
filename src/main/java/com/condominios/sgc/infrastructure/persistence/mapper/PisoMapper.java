package com.condominios.sgc.infrastructure.persistence.mapper;

import com.condominios.sgc.domain.model.PisoModel;
import com.condominios.sgc.infrastructure.persistence.entity.PisoEntity;

public class PisoMapper {

    public PisoModel aDominio(PisoEntity entidad) {
        return new PisoModel(entidad.getId(), entidad.getNumero(), java.util.Collections.emptyList());
    }

    public PisoEntity aEntidad(PisoModel dominio) {
        PisoEntity entidad = new PisoEntity();
        entidad.setId(dominio.getId());
        entidad.setNumero(dominio.getNumero());
        return entidad;
    }
}

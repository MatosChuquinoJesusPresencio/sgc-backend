package com.condominios.sgc.infrastructure.persistence.mapper.catalog;

import com.condominios.sgc.domain.model.catalog.Ciudad;
import com.condominios.sgc.infrastructure.persistence.entity.CiudadEntity;

public class CiudadMapper {

    public Ciudad aDominio(CiudadEntity entidad) {
        return new Ciudad(
            entidad.getId(),
            entidad.getNombre(),
            entidad.getIdPais()
        );
    }

    public CiudadEntity aEntidad(Ciudad dominio) {
        CiudadEntity entidad = new CiudadEntity();
        entidad.setId(dominio.id());
        entidad.setNombre(dominio.nombre());
        entidad.setIdPais(dominio.idPais());
        return entidad;
    }
}

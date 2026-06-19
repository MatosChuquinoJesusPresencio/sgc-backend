package com.condominios.sgc.infrastructure.persistence.mapper.catalog;

import com.condominios.sgc.domain.model.catalog.Pais;
import com.condominios.sgc.infrastructure.persistence.entity.PaisEntity;

public class PaisMapper {

    public Pais aDominio(PaisEntity entidad) {
        return new Pais(
            entidad.getId(),
            entidad.getNombre(),
            entidad.getCodigoIso(),
            entidad.getIdMoneda()
        );
    }

    public PaisEntity aEntidad(Pais dominio) {
        PaisEntity entidad = new PaisEntity();
        entidad.setId(dominio.id());
        entidad.setNombre(dominio.nombre());
        entidad.setCodigoIso(dominio.codigoIso());
        entidad.setIdMoneda(dominio.idMoneda());
        return entidad;
    }
}

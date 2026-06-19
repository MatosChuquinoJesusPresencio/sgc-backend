package com.condominios.sgc.infrastructure.persistence.mapper.catalog;

import com.condominios.sgc.domain.model.catalog.Moneda;
import com.condominios.sgc.infrastructure.persistence.entity.MonedaEntity;

public class MonedaMapper {

    public Moneda aDominio(MonedaEntity entidad) {
        return new Moneda(
            entidad.getId(),
            entidad.getNombre(),
            entidad.getCodigo(),
            entidad.getSimbolo()
        );
    }

    public MonedaEntity aEntidad(Moneda dominio) {
        MonedaEntity entidad = new MonedaEntity();
        entidad.setId(dominio.id());
        entidad.setNombre(dominio.nombre());
        entidad.setCodigo(dominio.codigo());
        entidad.setSimbolo(dominio.simbolo());
        return entidad;
    }
}

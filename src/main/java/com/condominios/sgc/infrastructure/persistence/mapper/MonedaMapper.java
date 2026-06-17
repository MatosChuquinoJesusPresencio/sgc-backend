package com.condominios.sgc.infrastructure.persistence.mapper;

import com.condominios.sgc.domain.model.MonedaModel;
import com.condominios.sgc.infrastructure.persistence.entity.MonedaEntity;
import org.springframework.stereotype.Component;

@Component
public class MonedaMapper {

    public MonedaModel aModelo(MonedaEntity entidad) {
        return new MonedaModel(
            entidad.getId(), entidad.getNombre(), entidad.getCodigo(),
            entidad.getSimbolo());
    }

    public MonedaEntity aEntidad(MonedaModel modelo) {
        MonedaEntity entidad = new MonedaEntity();
        entidad.setId(modelo.getId());
        entidad.setNombre(modelo.getNombre());
        entidad.setCodigo(modelo.getCodigo());
        entidad.setSimbolo(modelo.getSimbolo());
        return entidad;
    }
}

package com.condominios.sgc.infrastructure.persistence.mapper;

import com.condominios.sgc.domain.model.ApartamentoModel;
import com.condominios.sgc.infrastructure.persistence.entity.ApartamentoEntity;

public class ApartamentoMapper {

    public ApartamentoModel aDominio(ApartamentoEntity entidad) {
        return new ApartamentoModel(
            entidad.getId(),
            entidad.getNumero(),
            entidad.getDerechoEstacionamiento(),
            entidad.getMetraje(),
            entidad.getIdPropietario()
        );
    }

    public ApartamentoEntity aEntidad(ApartamentoModel dominio) {
        ApartamentoEntity entidad = new ApartamentoEntity();
        entidad.setId(dominio.getId());
        entidad.setNumero(dominio.getNumero());
        entidad.setDerechoEstacionamiento(dominio.getDerechoEstacionamiento());
        entidad.setMetraje(dominio.getMetraje());
        entidad.setIdPropietario(dominio.getIdPropietario());
        return entidad;
    }
}

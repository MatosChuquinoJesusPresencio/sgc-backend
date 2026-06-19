package com.condominios.sgc.infrastructure.persistence.mapper;

import com.condominios.sgc.domain.model.CarritoModel;
import com.condominios.sgc.infrastructure.persistence.entity.CarritoEntity;

public class CarritoMapper {

    public CarritoModel aDominio(CarritoEntity entidad) {
        return new CarritoModel(
            entidad.getId(),
            entidad.getCodigo(),
            entidad.getEstado(),
            entidad.getIdCondominio()
        );
    }

    public CarritoEntity aEntidad(CarritoModel dominio) {
        CarritoEntity entidad = new CarritoEntity();
        entidad.setId(dominio.getId());
        entidad.setCodigo(dominio.getCodigo());
        entidad.setEstado(dominio.getEstado());
        entidad.setIdCondominio(dominio.getIdCondominio());
        return entidad;
    }
}

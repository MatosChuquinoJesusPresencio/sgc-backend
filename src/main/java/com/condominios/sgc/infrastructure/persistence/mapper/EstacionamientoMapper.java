package com.condominios.sgc.infrastructure.persistence.mapper;

import com.condominios.sgc.domain.model.EstacionamientoModel;
import com.condominios.sgc.infrastructure.persistence.entity.EstacionamientoEntity;

public class EstacionamientoMapper {

    public EstacionamientoModel aDominio(EstacionamientoEntity entidad) {
        return new EstacionamientoModel(
            entidad.getId(),
            entidad.getNumero(),
            entidad.getTipoVehiculo(),
            entidad.getCapacidadMaxima(),
            entidad.getCantidadActual(),
            entidad.getDisponible(),
            entidad.getIdApartamento(),
            entidad.getIdCondominio()
        );
    }

    public EstacionamientoEntity aEntidad(EstacionamientoModel dominio) {
        EstacionamientoEntity entidad = new EstacionamientoEntity();
        entidad.setId(dominio.getId());
        entidad.setNumero(dominio.getNumero());
        entidad.setTipoVehiculo(dominio.getTipoVehiculo());
        entidad.setCapacidadMaxima(dominio.getCapacidadMaxima());
        entidad.setCantidadActual(dominio.getCantidadActual());
        entidad.setDisponible(dominio.getDisponible());
        entidad.setIdApartamento(dominio.getIdApartamento());
        entidad.setIdCondominio(dominio.getIdCondominio());
        return entidad;
    }
}

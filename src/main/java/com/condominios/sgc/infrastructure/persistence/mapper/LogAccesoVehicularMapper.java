package com.condominios.sgc.infrastructure.persistence.mapper;

import com.condominios.sgc.domain.model.LogAccesoVehicularModel;
import com.condominios.sgc.domain.shared.value_objects.PlacaVehiculo;
import com.condominios.sgc.infrastructure.persistence.entity.LogAccesoVehicularEntity;

public class LogAccesoVehicularMapper {

    public LogAccesoVehicularModel aDominio(LogAccesoVehicularEntity entidad) {
        return new LogAccesoVehicularModel(
            entidad.getId(),
            new PlacaVehiculo(entidad.getPlaca()),
            entidad.getOcupante(),
            entidad.getDatosInquilino(),
            entidad.getMetodo(),
            entidad.getFechaEntrada(),
            entidad.getFechaSalida(),
            entidad.getIdVehiculo(),
            entidad.getIdEstacionamiento()
        );
    }

    public LogAccesoVehicularEntity aEntidad(LogAccesoVehicularModel dominio) {
        LogAccesoVehicularEntity entidad = new LogAccesoVehicularEntity();
        entidad.setId(dominio.getId());
        entidad.setPlaca(dominio.getPlaca().valor());
        entidad.setOcupante(dominio.getOcupante());
        entidad.setDatosInquilino(dominio.getDatosInquilino());
        entidad.setMetodo(dominio.getMetodo());
        entidad.setFechaEntrada(dominio.getFechaEntrada());
        entidad.setFechaSalida(dominio.getFechaSalida());
        entidad.setIdVehiculo(dominio.getIdVehiculo());
        entidad.setIdEstacionamiento(dominio.getIdEstacionamiento());
        return entidad;
    }
}

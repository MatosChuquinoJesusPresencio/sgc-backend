package com.condominios.sgc.infrastructure.persistence.mapper;

import com.condominios.sgc.domain.model.LogAccesoVehicularModel;
import com.condominios.sgc.infrastructure.persistence.entity.EstacionamientoEntity;
import com.condominios.sgc.infrastructure.persistence.entity.LogAccesoVehicularEntity;
import com.condominios.sgc.infrastructure.persistence.entity.VehiculoEntity;
import org.springframework.stereotype.Component;

@Component
public class LogAccesoVehicularMapper {

    public LogAccesoVehicularModel aModelo(LogAccesoVehicularEntity entidad) {
        return new LogAccesoVehicularModel(
            entidad.getId(), entidad.getPlaca(), entidad.getOcupante(),
            entidad.getDatosInquilino(), entidad.getMetodo(),
            entidad.getFechaEntrada(), entidad.getFechaSalida(),
            entidad.getIdVehiculo(), entidad.getIdEstacionamiento());
    }

    public LogAccesoVehicularEntity aEntidad(LogAccesoVehicularModel modelo) {
        LogAccesoVehicularEntity entidad = new LogAccesoVehicularEntity();
        entidad.setId(modelo.getId());
        entidad.setPlaca(modelo.getPlaca());
        entidad.setOcupante(modelo.getOcupante());
        entidad.setDatosInquilino(modelo.getDatosInquilino());
        entidad.setMetodo(modelo.getMetodo());
        entidad.setFechaEntrada(modelo.getFechaEntrada());
        entidad.setFechaSalida(modelo.getFechaSalida());
        if (modelo.getIdVehiculo() != null) {
            VehiculoEntity referencia = new VehiculoEntity();
            referencia.setId(modelo.getIdVehiculo());
            entidad.setVehiculo(referencia);
        }
        if (modelo.getIdEstacionamiento() != null) {
            EstacionamientoEntity referencia = new EstacionamientoEntity();
            referencia.setId(modelo.getIdEstacionamiento());
            entidad.setEstacionamiento(referencia);
        }
        return entidad;
    }
}

package com.condominios.sgc.infrastructure.persistence.mapper;

import static com.condominios.sgc.domain.util.ValidacionUtil.idDe;

import com.condominios.sgc.domain.model.LogAccesoVehicularModel;
import com.condominios.sgc.infrastructure.persistence.entity.EstacionamientoEntity;
import com.condominios.sgc.infrastructure.persistence.entity.LogAccesoVehicularEntity;
import com.condominios.sgc.infrastructure.persistence.entity.VehiculoEntity;

public final class LogAccesoVehicularMapper {

    private LogAccesoVehicularMapper() {}

    public static LogAccesoVehicularEntity toEntity(LogAccesoVehicularModel model) {
        if (model == null) return null;
        LogAccesoVehicularEntity e = new LogAccesoVehicularEntity();
        e.setId(model.getId());
        e.setPlaca(model.getPlaca());
        e.setOcupante(model.getOcupante());
        e.setDatosInquilino(model.getDatosInquilino());
        e.setMetodo(model.getMetodo());
        e.setFechaEntrada(model.getFechaEntrada());
        e.setFechaSalida(model.getFechaSalida());
        return e;
    }

    public static LogAccesoVehicularModel toModel(LogAccesoVehicularEntity e) {
        if (e == null) return null;
        return new LogAccesoVehicularModel(
                e.getId(), 
                e.getPlaca(), 
                e.getOcupante(), 
                e.getDatosInquilino(),
                e.getMetodo(),
                idDe(e.getVehiculo(), VehiculoEntity::getId),
                idDe(e.getEstacionamiento(), EstacionamientoEntity::getId)
        );
    }
}

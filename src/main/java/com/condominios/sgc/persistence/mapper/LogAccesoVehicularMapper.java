package com.condominios.sgc.persistence.mapper;

import com.condominios.sgc.domain.model.LogAccesoVehicularModel;
import com.condominios.sgc.persistence.entity.LogAccesoVehicularEntity;

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
                e.getId(), e.getPlaca(), e.getOcupante(), e.getDatosInquilino(),
                e.getMetodo(),
                e.getVehiculo() != null ? e.getVehiculo().getId() : null,
                e.getEstacionamiento() != null ? e.getEstacionamiento().getId() : null);
    }
}

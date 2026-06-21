package com.condominios.sgc.infrastructure.adapter.out.persistence.mapper;

import com.condominios.sgc.domain.model.LogAccesoVehicularModel;
import com.condominios.sgc.domain.shared.valueobject.PlacaVehiculo;
import com.condominios.sgc.domain.type.MetodoEntrada;
import com.condominios.sgc.domain.type.TipoHabitante;
import com.condominios.sgc.infrastructure.adapter.out.persistence.entity.LogAccesoVehicularEntity;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public final class LogAccesoVehicularMapper {

    private LogAccesoVehicularMapper() {}

    public static LogAccesoVehicularModel toModel(LogAccesoVehicularEntity e) {
        if (e == null) return null;
        return new LogAccesoVehicularModel(
            e.getId(),
            new PlacaVehiculo(e.getPlaca()),
            TipoHabitante.valueOf(e.getOcupante()),
            e.getDatosInquilino(),
            MetodoEntrada.valueOf(e.getMetodo()),
            e.getFechaEntrada().toInstant(ZoneOffset.UTC),
            e.getFechaSalida() != null ? e.getFechaSalida().toInstant(ZoneOffset.UTC) : null,
            e.getIdVehiculo(),
            e.getIdEstacionamiento()
        );
    }

    public static LogAccesoVehicularEntity toEntity(LogAccesoVehicularModel m) {
        if (m == null) return null;
        LogAccesoVehicularEntity e = new LogAccesoVehicularEntity();
        e.setId(m.getId());
        e.setPlaca(m.getPlaca().valor());
        e.setOcupante(m.getOcupante().name());
        e.setDatosInquilino(m.getDatosInquilino());
        e.setMetodo(m.getMetodo().name());
        e.setFechaEntrada(LocalDateTime.ofInstant(m.getFechaEntrada(), ZoneOffset.UTC));
        e.setFechaSalida(m.getFechaSalida() != null ? LocalDateTime.ofInstant(m.getFechaSalida(), ZoneOffset.UTC) : null);
        e.setIdVehiculo(m.getIdVehiculo());
        e.setIdEstacionamiento(m.getIdEstacionamiento());
        return e;
    }
}

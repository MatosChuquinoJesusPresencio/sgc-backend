package com.condominios.sgc.infrastructure.adapter.out.persistence.mapper;

import com.condominios.sgc.domain.model.LogPrestamoCarritoModel;
import com.condominios.sgc.domain.type.TipoHabitante;
import com.condominios.sgc.infrastructure.adapter.out.persistence.entity.LogPrestamoCarritoEntity;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public final class LogPrestamoCarritoMapper {

    private LogPrestamoCarritoMapper() {}

    public static LogPrestamoCarritoModel toModel(LogPrestamoCarritoEntity e) {
        if (e == null) return null;
        return new LogPrestamoCarritoModel(
            e.getId(),
            TipoHabitante.valueOf(e.getSolicitante()),
            e.getNombreSolicitante(),
            e.getDniSolicitante(),
            e.getPenalizacion(),
            e.getFechaPrestamo().toInstant(ZoneOffset.UTC),
            e.getFechaDevolucion() != null ? e.getFechaDevolucion().toInstant(ZoneOffset.UTC) : null,
            e.getIdApartamento(),
            e.getIdCarrito(),
            e.getIdInquilino(),
            e.getIdPropietario()
        );
    }

    public static LogPrestamoCarritoEntity toEntity(LogPrestamoCarritoModel m) {
        if (m == null) return null;
        LogPrestamoCarritoEntity e = new LogPrestamoCarritoEntity();
        e.setId(m.getId());
        e.setSolicitante(m.getSolicitante().name());
        e.setNombreSolicitante(m.getNombreSolicitante());
        e.setDniSolicitante(m.getDniSolicitante());
        e.setPenalizacion(m.getPenalizacion());
        e.setFechaPrestamo(LocalDateTime.ofInstant(m.getFechaPrestamo(), ZoneOffset.UTC));
        e.setFechaDevolucion(m.getFechaDevolucion() != null ? LocalDateTime.ofInstant(m.getFechaDevolucion(), ZoneOffset.UTC) : null);
        e.setIdApartamento(m.getIdApartamento());
        e.setIdCarrito(m.getIdCarrito());
        e.setIdInquilino(m.getIdInquilino());
        e.setIdPropietario(m.getIdPropietario());
        return e;
    }
}

package com.condominios.sgc.infrastructure.persistence.mapper;

import com.condominios.sgc.domain.model.LogPrestamoCarritoModel;
import com.condominios.sgc.infrastructure.persistence.entity.LogPrestamoCarritoEntity;

public final class LogPrestamoCarritoMapper {

    private LogPrestamoCarritoMapper() {}

    public static LogPrestamoCarritoEntity toEntity(LogPrestamoCarritoModel model) {
        if (model == null) return null;
        LogPrestamoCarritoEntity e = new LogPrestamoCarritoEntity();
        e.setId(model.getId());
        e.setSolicitante(model.getSolicitante());
        e.setPenalizacion(model.getPenalizacion());
        e.setFechaPrestamo(model.getFechaPrestamo());
        e.setFechaDevolucion(model.getFechaDevolucion());
        return e;
    }

    public static LogPrestamoCarritoModel toModel(LogPrestamoCarritoEntity e) {
        if (e == null) return null;
        LogPrestamoCarritoModel m = new LogPrestamoCarritoModel(
                e.getId(), e.getSolicitante(),
                e.getApartamento() != null ? e.getApartamento().getId() : null,
                e.getCarrito() != null ? e.getCarrito().getId() : null);
        if (e.getUsuario() != null) {
            m.asignarUsuarioSolicitante(e.getUsuario().getId());
        }
        if (e.getInquilino() != null) {
            m.asignarInquilinoSolicitante(e.getInquilino().getId());
        }
        return m;
    }
}

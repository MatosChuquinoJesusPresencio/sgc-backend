package com.condominios.sgc.infrastructure.persistence.mapper;

import static com.condominios.sgc.domain.util.EntidadUtil.idDe;

import com.condominios.sgc.domain.model.LogPrestamoCarritoModel;
import com.condominios.sgc.infrastructure.persistence.entity.ApartamentoEntity;
import com.condominios.sgc.infrastructure.persistence.entity.CarritoEntity;
import com.condominios.sgc.infrastructure.persistence.entity.InquilinoEntity;
import com.condominios.sgc.infrastructure.persistence.entity.LogPrestamoCarritoEntity;
import com.condominios.sgc.infrastructure.persistence.entity.UsuarioEntity;

public final class LogPrestamoCarritoMapper {

    private LogPrestamoCarritoMapper() {}

    public static LogPrestamoCarritoEntity toEntity(LogPrestamoCarritoModel model) {
        if (model == null) return null;
        LogPrestamoCarritoEntity e = new LogPrestamoCarritoEntity();
        e.setId(model.getId());
        e.setSolicitante(model.getSolicitante());
        e.setNombreSolicitante(model.getNombreSolicitante());
        e.setDniSolicitante(model.getDniSolicitante());
        e.setPenalizacion(model.getPenalizacion());
        e.setFechaPrestamo(model.getFechaPrestamo());
        e.setFechaDevolucion(model.getFechaDevolucion());
        return e;
    }

    public static LogPrestamoCarritoModel toModel(LogPrestamoCarritoEntity e) {
        if (e == null) return null;
        return new LogPrestamoCarritoModel(
                e.getId(),
                e.getSolicitante(),
                e.getNombreSolicitante(),
                e.getDniSolicitante(),
                idDe(e.getApartamento(), ApartamentoEntity::getId),
                idDe(e.getCarrito(), CarritoEntity::getId),
                idDe(e.getPropietario(), UsuarioEntity::getId),
                idDe(e.getInquilino(), InquilinoEntity::getId),
                e.getPenalizacion(),
                e.getFechaPrestamo(),
                e.getFechaDevolucion()
        );
    }
}

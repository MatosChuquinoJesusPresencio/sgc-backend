package com.condominios.sgc.infrastructure.persistence.mapper;

import com.condominios.sgc.domain.model.LogPrestamoCarritoModel;
import com.condominios.sgc.infrastructure.persistence.entity.LogPrestamoCarritoEntity;

public class LogPrestamoCarritoMapper {

    public LogPrestamoCarritoModel aDominio(LogPrestamoCarritoEntity entidad) {
        return new LogPrestamoCarritoModel(
            entidad.getId(),
            entidad.getSolicitante(),
            entidad.getNombreSolicitante(),
            entidad.getDniSolicitante(),
            entidad.getPenalizacion(),
            entidad.getFechaPrestamo(),
            entidad.getFechaDevolucion(),
            entidad.getIdApartamento(),
            entidad.getIdCarrito(),
            entidad.getIdInquilino(),
            entidad.getIdPropietario()
        );
    }

    public LogPrestamoCarritoEntity aEntidad(LogPrestamoCarritoModel dominio) {
        LogPrestamoCarritoEntity entidad = new LogPrestamoCarritoEntity();
        entidad.setId(dominio.getId());
        entidad.setSolicitante(dominio.getSolicitante());
        entidad.setNombreSolicitante(dominio.getNombreSolicitante());
        entidad.setDniSolicitante(dominio.getDniSolicitante());
        entidad.setPenalizacion(dominio.getPenalizacion());
        entidad.setFechaPrestamo(dominio.getFechaPrestamo());
        entidad.setFechaDevolucion(dominio.getFechaDevolucion());
        entidad.setIdApartamento(dominio.getIdApartamento());
        entidad.setIdCarrito(dominio.getIdCarrito());
        entidad.setIdInquilino(dominio.getIdInquilino());
        entidad.setIdPropietario(dominio.getIdPropietario());
        return entidad;
    }
}

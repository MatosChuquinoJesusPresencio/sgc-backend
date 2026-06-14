package com.condominios.sgc.application.dto.response;

import java.math.BigDecimal;
import java.time.Instant;

import com.condominios.sgc.domain.auxiliar.TipoHabitante;
import com.condominios.sgc.domain.model.LogPrestamoCarritoModel;

public record LogPrestamoCarritoResponse(
    Long id,
    TipoHabitante solicitante,
    String nombreSolicitante,
    String dniSolicitante,
    BigDecimal penalizacion,
    Instant fechaPrestamo,
    Instant fechaDevolucion,
    Long idApartamento,
    Long idCarrito,
    Long idInquilino,
    Long idPropietario
) {
    public static LogPrestamoCarritoResponse desdeModelo(LogPrestamoCarritoModel model) {
        return new LogPrestamoCarritoResponse(
            model.getId(), model.getSolicitante(), model.getNombreSolicitante(),
            model.getDniSolicitante(), model.getPenalizacion(),
            model.getFechaPrestamo(), model.getFechaDevolucion(),
            model.getIdApartamento(), model.getIdCarrito(),
            model.getIdInquilino(), model.getIdPropietario());
    }
}

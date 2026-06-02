package com.condominios.sgc.web.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.condominios.sgc.domain.auxiliar.TipoHabitante;
import com.condominios.sgc.domain.model.LogPrestamoCarritoModel;

public record LogPrestamoCarritoResponse(
    Long id,
    TipoHabitante solicitante,
    String nombreSolicitante,
    String dniSolicitante,
    BigDecimal penalizacion,
    LocalDateTime fechaPrestamo,
    LocalDateTime fechaDevolucion,
    Long apartamentoId,
    Long carritoId,
    Long inquilinoId,
    Long propietarioId
) {
    public static LogPrestamoCarritoResponse fromModel(LogPrestamoCarritoModel model) {
        return new LogPrestamoCarritoResponse(
            model.getId(),
            model.getSolicitante(),
            model.getNombreSolicitante(),
            model.getDniSolicitante(),
            model.getPenalizacion(),
            model.getFechaPrestamo(),
            model.getFechaDevolucion(),
            model.getApartamentoId(),
            model.getCarritoId(),
            model.getInquilinoId(),
            model.getPropietarioId()
        );
    }
}

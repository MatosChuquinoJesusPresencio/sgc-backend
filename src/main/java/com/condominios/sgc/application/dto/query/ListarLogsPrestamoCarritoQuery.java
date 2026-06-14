package com.condominios.sgc.application.dto.query;

import java.time.Instant;

import com.condominios.sgc.domain.auxiliar.TipoHabitante;

public record ListarLogsPrestamoCarritoQuery(
    int pagina,
    int tamano,
    String nombreSolicitante,
    String dniSolicitante,
    TipoHabitante solicitante,
    Long idApartamento,
    Long idCarrito,
    Instant fechaDesde,
    Instant fechaHasta,
    Boolean sinDevolucion
) {}

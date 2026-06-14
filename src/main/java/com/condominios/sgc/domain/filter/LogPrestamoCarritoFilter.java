package com.condominios.sgc.domain.filter;

import com.condominios.sgc.domain.auxiliar.TipoHabitante;
import java.time.Instant;

public record LogPrestamoCarritoFilter(
    String nombreSolicitante,
    String dniSolicitante,
    TipoHabitante solicitante,
    Long idApartamento,
    Long idCarrito,
    Instant fechaDesde,
    Instant fechaHasta,
    Boolean sinDevolucion
) {}


package com.condominios.sgc.infrastructure.adapter.in.web.dto.response;

import java.math.BigDecimal;
import java.time.Instant;

public record SecurityCartLoanFullResponse(
    Long id,
    String solicitante,
    String nombreSolicitante,
    String dniSolicitante,
    String codigoCarrito,
    Long idApartamento,
    Long idCarrito,
    Instant fechaPrestamo,
    Instant fechaDevolucion,
    BigDecimal penalizacion
) {
}

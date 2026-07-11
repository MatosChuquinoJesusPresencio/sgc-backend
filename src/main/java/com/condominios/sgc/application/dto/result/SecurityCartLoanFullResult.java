package com.condominios.sgc.application.dto.result;

import java.math.BigDecimal;
import java.time.Instant;

public record SecurityCartLoanFullResult(
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

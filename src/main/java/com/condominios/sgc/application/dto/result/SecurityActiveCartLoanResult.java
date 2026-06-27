package com.condominios.sgc.application.dto.result;

import java.math.BigDecimal;
import java.time.Instant;

public record SecurityActiveCartLoanResult(
    Long id,
    String nombreSolicitante,
    String dniSolicitante,
    String codigoCarrito,
    Instant fechaPrestamo,
    BigDecimal penalizacion
) {
}

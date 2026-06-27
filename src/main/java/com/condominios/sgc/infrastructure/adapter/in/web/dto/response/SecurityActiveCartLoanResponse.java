package com.condominios.sgc.infrastructure.adapter.in.web.dto.response;

import java.math.BigDecimal;
import java.time.Instant;

public record SecurityActiveCartLoanResponse(
    Long id,
    String nombreSolicitante,
    String dniSolicitante,
    String codigoCarrito,
    Instant fechaPrestamo,
    BigDecimal penalizacion
) {
}

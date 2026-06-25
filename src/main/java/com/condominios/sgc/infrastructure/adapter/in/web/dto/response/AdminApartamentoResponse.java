package com.condominios.sgc.infrastructure.adapter.in.web.dto.response;

import java.math.BigDecimal;

public record AdminApartamentoResponse(
    Long id,
    Integer numero,
    BigDecimal metraje,
    Boolean derechoEstacionamiento,
    Long idPropietario
) {
}

package com.condominios.sgc.application.dto.result;

import java.math.BigDecimal;

public record AdminApartamentoResult(
    Long id,
    Integer numero,
    BigDecimal metraje,
    Boolean derechoEstacionamiento,
    Long idPropietario
) {
}

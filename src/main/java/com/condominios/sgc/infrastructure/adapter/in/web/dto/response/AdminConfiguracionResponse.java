package com.condominios.sgc.infrastructure.adapter.in.web.dto.response;

import java.math.BigDecimal;

public record AdminConfiguracionResponse(
    Integer maxAutos,
    Integer maxMotos,
    BigDecimal penalizacionPorMin,
    Integer maxTiempoPrestamoMin,
    Integer maxEstacionamientosPorDepto,
    Integer maxCarritosPorDepto,
    Integer maxVehiculosPorDepto,
    Integer maxInquilinosPorDepto
) {
}

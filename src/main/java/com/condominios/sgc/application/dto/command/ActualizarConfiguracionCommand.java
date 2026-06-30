package com.condominios.sgc.application.dto.command;

import java.math.BigDecimal;

public record ActualizarConfiguracionCommand(
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

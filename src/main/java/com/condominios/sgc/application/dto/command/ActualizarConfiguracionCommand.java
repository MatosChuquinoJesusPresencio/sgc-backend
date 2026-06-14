package com.condominios.sgc.application.dto.command;

import java.math.BigDecimal;

public record ActualizarConfiguracionCommand(
    Integer maxAutos,
    Integer maxMotos,
    BigDecimal penalizacionPorMin,
    Integer maxTiempoPrestamoMin,
    Integer maxEstacionamientosPorApartamento,
    Integer maxCarritosPorApartamento,
    Integer maxVehiculosPorPropietario,
    Integer maxInquilinosPorApartamento
) {}

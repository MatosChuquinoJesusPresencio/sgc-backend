package com.condominios.sgc.application.dto;

import java.math.BigDecimal;

public record ActualizarConfiguracionRequest(
    Integer maxAutos,
    Integer maxMotos,
    BigDecimal penalizacionPorMin,
    Integer maxTiempoPrestamoMin,
    Integer maxEstacionamientosPorApartamento,
    Integer maxCarritosPorApartamento,
    Integer maxVehiculosPorPropietario,
    Integer maxInquilinosPorApartamento
) {}

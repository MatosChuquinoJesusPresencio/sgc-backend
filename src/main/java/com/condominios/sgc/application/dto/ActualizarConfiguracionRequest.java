package com.condominios.sgc.application.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record ActualizarConfiguracionRequest(
    @NotNull Integer maxAutos,
    @NotNull Integer maxMotos,
    @NotNull BigDecimal penalizacionPorMin,
    @NotNull Integer maxTiempoPrestamoMin,
    @NotNull Integer maxEstacionamientosPorApartamento,
    @NotNull Integer maxCarritosPorApartamento,
    @NotNull Integer maxVehiculosPorPropietario,
    @NotNull Integer maxInquilinosPorApartamento
) {}
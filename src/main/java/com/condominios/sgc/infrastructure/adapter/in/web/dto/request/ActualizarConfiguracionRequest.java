package com.condominios.sgc.infrastructure.adapter.in.web.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ActualizarConfiguracionRequest(
    @NotNull @Positive Integer maxAutos,
    @NotNull @Positive Integer maxMotos,
    @NotNull @Positive BigDecimal penalizacionPorMin,
    @NotNull @Positive Integer maxTiempoPrestamoMin,
    @NotNull @Positive Integer maxEstacionamientos,
    @NotNull @Positive Integer maxCarritos,
    @NotNull @Positive Integer maxVehiculos,
    @NotNull @Positive Integer maxInquilinos
) {
}

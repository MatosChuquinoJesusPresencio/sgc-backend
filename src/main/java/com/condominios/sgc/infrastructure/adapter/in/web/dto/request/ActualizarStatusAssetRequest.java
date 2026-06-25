package com.condominios.sgc.infrastructure.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ActualizarStatusAssetRequest(
    @NotBlank String tipo,
    @NotBlank String estado,
    @NotNull Boolean disponible,
    @NotBlank String tipoVehiculo,
    @NotNull Integer capacidadMaxima
) {
}

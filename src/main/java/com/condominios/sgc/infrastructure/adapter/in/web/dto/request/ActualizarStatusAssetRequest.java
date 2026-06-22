package com.condominios.sgc.infrastructure.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ActualizarStatusAssetRequest(
    @NotBlank String tipo,
    String estado,
    Boolean disponible,
    String tipoVehiculo,
    Integer capacidadMaxima
) {
}

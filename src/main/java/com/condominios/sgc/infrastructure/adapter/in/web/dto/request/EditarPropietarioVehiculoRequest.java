package com.condominios.sgc.infrastructure.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;

public record EditarPropietarioVehiculoRequest(
    @NotBlank String marca,
    @NotBlank String color,
    @NotBlank String modelo,
    @NotBlank String placa,
    Long inquilinoId
) {
}

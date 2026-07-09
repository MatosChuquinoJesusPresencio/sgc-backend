package com.condominios.sgc.infrastructure.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CrearPropietarioVehiculoRequest(
    @NotBlank String marca,
    @NotBlank String color,
    @NotBlank String modelo,
    @NotBlank String placa,
    @NotBlank String tipo,
    Long inquilinoId
) {
}

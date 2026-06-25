package com.condominios.sgc.infrastructure.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CrearPropietarioVehiculoRequest(
        @NotBlank String marca,
    String color,
    String modelo,
        @NotBlank String placa,
    String tipo
) {
}

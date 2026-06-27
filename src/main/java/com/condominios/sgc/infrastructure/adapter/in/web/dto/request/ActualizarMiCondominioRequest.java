package com.condominios.sgc.infrastructure.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ActualizarMiCondominioRequest(
    @NotBlank String nombre,
    @NotBlank String direccion
) {
}

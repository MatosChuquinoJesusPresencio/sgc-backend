package com.condominios.sgc.infrastructure.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CrearAssetRequest(
    @NotBlank String tipo,
    String codigo,
    Integer numero
) {
}

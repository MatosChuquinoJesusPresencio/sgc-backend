package com.condominios.sgc.infrastructure.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CrearAssetRequest(
    @NotBlank String tipo,
    @NotBlank String codigo,
    @NotNull Integer numero
) {
}

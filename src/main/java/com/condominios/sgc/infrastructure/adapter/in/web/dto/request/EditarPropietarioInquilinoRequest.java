package com.condominios.sgc.infrastructure.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;

public record EditarPropietarioInquilinoRequest(
    @NotBlank String nombres,
    @NotBlank String apellidos,
    @NotBlank String tipoDocumento,
    @NotBlank String numeroDocumento
) {
}

package com.condominios.sgc.infrastructure.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CrearPropietarioInquilinoRequest(
        @NotBlank String nombres,
    String apellidos,
    String tipoDocumento,
    String numeroDocumento
) {
}

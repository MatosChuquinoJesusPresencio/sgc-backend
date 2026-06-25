package com.condominios.sgc.infrastructure.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ActualizarAdminUserRequest(
    @NotBlank String nombres,
    @NotBlank String apellidos,
    String telefono
) {
}

package com.condominios.sgc.infrastructure.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ActualizarAdminUserRequest(
    @NotBlank String nombres,
    @NotBlank String apellidos,
    @NotBlank @Size(min = 7, max = 16) String telefono,
    String rol
) {
}

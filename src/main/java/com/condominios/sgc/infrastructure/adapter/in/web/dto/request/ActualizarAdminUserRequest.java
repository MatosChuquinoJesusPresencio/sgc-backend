package com.condominios.sgc.infrastructure.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ActualizarAdminUserRequest(
    @NotBlank String nombres,
    @NotBlank String apellidos,

    @NotBlank
    @Size(max = 9, message = "El teléfono debe tener 9 caracteres")
    String telefono
) {
}

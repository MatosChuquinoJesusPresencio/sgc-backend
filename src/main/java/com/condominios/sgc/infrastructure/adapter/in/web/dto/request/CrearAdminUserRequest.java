package com.condominios.sgc.infrastructure.adapter.in.web.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CrearAdminUserRequest(
    @NotBlank String nombres,
    @NotBlank String apellidos,
    @NotBlank @Email String correo,
    @NotBlank @Size(min = 7, max = 16) String telefono,
    @NotBlank String contrasena,
    @NotBlank String rol
) {
}

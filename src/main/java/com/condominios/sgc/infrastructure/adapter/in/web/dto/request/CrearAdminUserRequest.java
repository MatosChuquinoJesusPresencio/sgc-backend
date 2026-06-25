package com.condominios.sgc.infrastructure.adapter.in.web.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CrearAdminUserRequest(
    @NotBlank String nombres,
    @NotBlank String apellidos,
    @NotBlank @Email String correo,

    @NotBlank
    @Size(max = 9, message = "El teléfono debe tener entre 7 y 15 caracteres")
    String telefono,
    @NotBlank String contrasena,
    @NotBlank String rol
) {
}

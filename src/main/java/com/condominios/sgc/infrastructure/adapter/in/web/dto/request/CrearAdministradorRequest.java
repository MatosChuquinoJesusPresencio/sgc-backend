package com.condominios.sgc.infrastructure.adapter.in.web.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CrearAdministradorRequest(
    @NotBlank String nombres,
    @NotBlank String apellidos,
    @NotBlank @Email String correo,
    String telefono,
    @NotBlank String contrasena
) {
}

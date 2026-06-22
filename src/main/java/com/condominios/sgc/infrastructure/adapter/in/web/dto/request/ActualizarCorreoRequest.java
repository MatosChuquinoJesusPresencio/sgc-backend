package com.condominios.sgc.infrastructure.adapter.in.web.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ActualizarCorreoRequest(
    @NotBlank @Email String nuevoCorreo,
    @NotBlank String contrasena
) {}

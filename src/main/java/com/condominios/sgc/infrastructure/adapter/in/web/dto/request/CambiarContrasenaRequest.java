package com.condominios.sgc.infrastructure.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CambiarContrasenaRequest(
    @NotBlank String contrasenaActual,
    @NotBlank @Size(min = 8) String nuevaContrasena
) {}

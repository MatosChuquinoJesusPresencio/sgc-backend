package com.condominios.sgc.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CambiarContrasenaRequest(
    @NotBlank String contrasenaActual,
    @NotBlank @Size(min = 8) String nuevaContrasena
) {}

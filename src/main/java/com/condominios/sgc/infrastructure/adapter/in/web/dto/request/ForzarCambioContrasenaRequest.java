package com.condominios.sgc.infrastructure.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ForzarCambioContrasenaRequest(
    @NotBlank String nuevaContrasena
) {
}

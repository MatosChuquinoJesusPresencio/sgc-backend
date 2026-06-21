package com.condominios.sgc.infrastructure.adapter.in.web.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Solicitud para restablecer la contrasena con token")
public record RestablecerContrasenaRequest(
    @NotBlank @Schema(description = "Token de restablecimiento") String token,
    @NotBlank @Size(min = 8) @Schema(description = "Nueva contrasena (min. 8 caracteres)") String nuevaContrasena
) {}

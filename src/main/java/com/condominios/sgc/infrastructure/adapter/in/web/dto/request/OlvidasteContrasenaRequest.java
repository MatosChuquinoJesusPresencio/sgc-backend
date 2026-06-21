package com.condominios.sgc.infrastructure.adapter.in.web.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Solicitud de restablecimiento de contrasena")
public record OlvidasteContrasenaRequest(
    @NotBlank @Email @Schema(description = "Correo electronico del usuario") String correo
) {}

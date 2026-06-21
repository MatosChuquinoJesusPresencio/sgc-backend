package com.condominios.sgc.infrastructure.adapter.in.web.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Solicitud de actualizacion de correo electronico")
public record ActualizarCorreoRequest(
    @NotBlank @Email @Schema(description = "Nuevo correo electronico") String nuevoCorreo,
    @NotBlank @Schema(description = "Contrasena actual para confirmar") String contrasena
) {}

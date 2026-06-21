package com.condominios.sgc.infrastructure.adapter.in.web.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Solicitud de actualizacion de perfil")
public record ActualizarPerfilRequest(
    @NotBlank @Schema(description = "Nombres del usuario") String nombres,
    @NotBlank @Schema(description = "Apellidos del usuario") String apellidos,
    @Schema(description = "Telefono del usuario") String telefono
) {}

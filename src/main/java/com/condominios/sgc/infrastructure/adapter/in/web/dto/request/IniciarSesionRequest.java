package com.condominios.sgc.infrastructure.adapter.in.web.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Solicitud de inicio de sesion")
public record IniciarSesionRequest(
    @NotBlank @Schema(description = "Correo electronico del usuario") String correo,
    @NotBlank @Schema(description = "Contrasena del usuario") String contrasena,
    @Schema(description = "Indica si se debe recordar la sesion") Boolean recuerdame
) {}

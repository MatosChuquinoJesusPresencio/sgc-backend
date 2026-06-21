package com.condominios.sgc.infrastructure.adapter.in.web.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Solicitud de creacion de usuario")
public record CrearUsuarioRequest(
    @NotBlank @Schema(description = "Nombres del usuario") String nombres,
    @NotBlank @Schema(description = "Apellidos del usuario") String apellidos,
    @NotBlank @Email @Schema(description = "Correo electronico") String correo,
    @NotBlank @Schema(description = "Numero de telefono") String telefono,
    @NotBlank @Schema(description = "Rol del usuario (SUPER_ADMINISTRADOR, ADMINISTRADOR_CONDOMINIO, PROPIETARIO, AGENTE_SEGURIDAD)") String rol,
    @NotNull @Schema(description = "ID del condominio al que pertenece") Long idCondominio,
    @NotBlank @Size(min = 8) @Schema(description = "Contrasena (min. 8 caracteres)") String contrasena
) {}

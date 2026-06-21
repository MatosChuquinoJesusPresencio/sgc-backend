package com.condominios.sgc.infrastructure.adapter.in.web.dto.response;

import java.time.Instant;

import com.condominios.sgc.domain.type.Rol;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Informacion completa del perfil del usuario")
public record PerfilResponse(
    Long id,
    String nombres,
    String apellidos,
    String correo,
    String telefono,
    Rol rol,
    Boolean activo,
    Boolean correoVerificado,
    Long idCondominio,
    Instant fechaCreacion
) {}

package com.condominios.sgc.infrastructure.adapter.in.web.dto.response;

import com.condominios.sgc.domain.type.Rol;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Informacion basica del usuario")
public record UsuarioResponse(
    Long id,
    String correo,
    String nombres,
    String apellidos,
    String telefono,
    Rol rol,
    Boolean activo,
    Boolean correoVerificado,
    Long idCondominio
) {}

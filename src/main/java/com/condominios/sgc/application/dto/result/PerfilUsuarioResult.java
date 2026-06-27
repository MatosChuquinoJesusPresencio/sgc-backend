package com.condominios.sgc.application.dto.result;

import java.time.Instant;

import com.condominios.sgc.domain.type.Rol;

public record PerfilUsuarioResult(
    Long id,
    String correo,
    String nombres,
    String apellidos,
    String telefono,
    Rol rol,
    Boolean activo,
    Boolean correoVerificado,
    Long idCondominio,
    Instant fechaCreacion
) {}

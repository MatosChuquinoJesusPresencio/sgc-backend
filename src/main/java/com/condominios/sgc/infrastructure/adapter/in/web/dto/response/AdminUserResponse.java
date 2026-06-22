package com.condominios.sgc.infrastructure.adapter.in.web.dto.response;

import java.time.Instant;

public record AdminUserResponse(
    Long id,
    String nombres,
    String apellidos,
    String correo,
    String telefono,
    String rol,
    Boolean activo,
    Instant fechaCreacion
) {
}

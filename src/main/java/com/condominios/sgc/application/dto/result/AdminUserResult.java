package com.condominios.sgc.application.dto.result;

import java.time.Instant;

public record AdminUserResult(
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

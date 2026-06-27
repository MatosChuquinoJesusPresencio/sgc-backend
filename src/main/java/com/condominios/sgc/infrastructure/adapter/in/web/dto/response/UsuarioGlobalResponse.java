package com.condominios.sgc.infrastructure.adapter.in.web.dto.response;

import java.time.Instant;

public record UsuarioGlobalResponse(
    Long id,
    String nombres,
    String apellidos,
    String correo,
    String telefono,
    String rol,
    Boolean activo,
    Boolean correoVerificado,
    Long idCondominio,
    String nombreCondominio,
    Instant fechaCreacion
) {
}

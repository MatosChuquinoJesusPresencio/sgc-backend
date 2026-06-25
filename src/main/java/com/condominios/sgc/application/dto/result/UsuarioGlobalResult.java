package com.condominios.sgc.application.dto.result;

import java.time.Instant;

public record UsuarioGlobalResult(
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

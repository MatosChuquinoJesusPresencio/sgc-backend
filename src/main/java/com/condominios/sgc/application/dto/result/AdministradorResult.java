package com.condominios.sgc.application.dto.result;

import java.time.Instant;

public record AdministradorResult(
    Long id,
    String nombres,
    String apellidos,
    String correo,
    String telefono,
    Boolean activo,
    Long idCondominio,
    String nombreCondominio,
    Instant fechaCreacion
) {
}

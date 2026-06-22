package com.condominios.sgc.infrastructure.adapter.in.web.dto.response;

import java.time.Instant;

public record AdministradorResponse(
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

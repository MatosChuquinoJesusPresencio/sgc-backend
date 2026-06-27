package com.condominios.sgc.infrastructure.adapter.in.web.dto.response;

import java.time.Instant;

public record CondominioSimpleResponse(
    Long id,
    String nombre,
    String direccion,
    String nombrePais,
    String nombreCiudad,
    String nombreAdministrador,
    Instant fechaCreacion
) {
}

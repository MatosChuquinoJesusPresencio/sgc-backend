package com.condominios.sgc.infrastructure.adapter.in.web.dto.response;

import java.time.Instant;

public record CondominioResponse(
    Long id,
    String nombre,
    Long idPais,
    String nombrePais,
    Long idCiudad,
    String nombreCiudad,
    String direccion,
    Boolean activo,
    Long idAdministrador,
    String nombreAdministrador,
    Instant fechaCreacion
) {
}

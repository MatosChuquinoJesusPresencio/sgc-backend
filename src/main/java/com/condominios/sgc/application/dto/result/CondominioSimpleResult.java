package com.condominios.sgc.application.dto.result;

import java.time.Instant;

public record CondominioSimpleResult(
    Long id,
    String nombre,
    String direccion,
    String nombrePais,
    String nombreCiudad,
    String nombreAdministrador,
    Instant fechaCreacion
) {
}

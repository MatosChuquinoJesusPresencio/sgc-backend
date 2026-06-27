package com.condominios.sgc.application.dto.result;

import java.time.Instant;

public record CondominioResult(
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

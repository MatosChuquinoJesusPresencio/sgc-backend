package com.condominios.sgc.web.dto.response;

import java.time.Instant;

public record CondominioResponse(
    Long id,
    String nombre,
    String nombrePais,
    String nombreCiudad,
    String direccion,
    Instant fechaCreacion
) {
    public static CondominioResponse desdeAplicacion(com.condominios.sgc.application.dto.response.CondominioResponse app) {
        return new CondominioResponse(app.id(), app.nombre(), app.nombrePais(),
                app.nombreCiudad(), app.direccion(), app.fechaCreacion());
    }
}
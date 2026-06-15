package com.condominios.sgc.web.dto.response;

import java.time.Instant;

public record CondominioResponse(
    Long id,
    String nombre,
    Long idPais,
    Long idCiudad,
    String direccion,
    Instant fechaCreacion
) {
    public static CondominioResponse desdeAplicacion(com.condominios.sgc.application.dto.response.CondominioResponse app) {
        return new CondominioResponse(app.id(), app.nombre(), app.idPais(),
                app.idCiudad(), app.direccion(), app.fechaCreacion());
    }
}
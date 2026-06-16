package com.condominios.sgc.web.dto.response;

public record TorreResponse(
    Long id,
    String nombre,
    Long idCondominio
) {
    public static TorreResponse desdeAplicacion(com.condominios.sgc.application.dto.response.TorreResponse app) {
        return new TorreResponse(app.id(), app.nombre(), app.idCondominio());
    }
}

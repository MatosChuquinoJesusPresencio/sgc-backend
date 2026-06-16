package com.condominios.sgc.web.dto.response;

public record PisoResponse(
    Long id,
    Integer numero,
    Long idTorre
) {
    public static PisoResponse desdeAplicacion(com.condominios.sgc.application.dto.response.PisoResponse app) {
        return new PisoResponse(app.id(), app.numero(), app.idTorre());
    }
}

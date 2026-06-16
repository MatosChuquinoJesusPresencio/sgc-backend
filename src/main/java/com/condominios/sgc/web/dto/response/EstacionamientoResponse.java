package com.condominios.sgc.web.dto.response;

import com.condominios.sgc.domain.auxiliar.TipoVehiculo;

public record EstacionamientoResponse(
    Long id,
    Integer numero,
    TipoVehiculo tipoVehiculo,
    Integer capacidadMaxima,
    Integer cantidadActual,
    Boolean disponible,
    Long idApartamento,
    Long idCondominio
) {
    public static EstacionamientoResponse desdeAplicacion(com.condominios.sgc.application.dto.response.EstacionamientoResponse app) {
        return new EstacionamientoResponse(app.id(), app.numero(), app.tipoVehiculo(),
                app.capacidadMaxima(), app.cantidadActual(), app.disponible(),
                app.idApartamento(), app.idCondominio());
    }
}

package com.condominios.sgc.web.dto.response;

import java.math.BigDecimal;

public record ApartamentoResponse(
    Long id,
    Integer numero,
    Boolean derechoEstacionamiento,
    BigDecimal metraje,
    Long idPropietario,
    Long idPiso
) {
    public static ApartamentoResponse desdeAplicacion(com.condominios.sgc.application.dto.response.ApartamentoResponse app) {
        return new ApartamentoResponse(app.id(), app.numero(), app.derechoEstacionamiento(),
                app.metraje(), app.idPropietario(), app.idPiso());
    }
}

package com.condominios.sgc.web.dto.response;

import java.math.BigDecimal;

public record ConfiguracionResponse(
    Long id,
    Integer maxAutos,
    Integer maxMotos,
    BigDecimal penalizacionPorMin,
    Integer maxTiempoPrestamoMin,
    Integer maxEstacionamientosPorApartamento,
    Integer maxCarritosPorApartamento,
    Integer maxVehiculosPorPropietario,
    Integer maxInquilinosPorApartamento,
    Long idCondominio
) {
    public static ConfiguracionResponse desdeAplicacion(com.condominios.sgc.application.dto.response.ConfiguracionResponse app) {
        return new ConfiguracionResponse(app.id(), app.maxAutos(), app.maxMotos(),
                app.penalizacionPorMin(), app.maxTiempoPrestamoMin(),
                app.maxEstacionamientosPorApartamento(), app.maxCarritosPorApartamento(),
                app.maxVehiculosPorPropietario(), app.maxInquilinosPorApartamento(),
                app.idCondominio());
    }
}
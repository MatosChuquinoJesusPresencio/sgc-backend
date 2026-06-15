package com.condominios.sgc.web.dto.response;

public record DetalleCondominioResponse(
    CondominioResponse condominio,
    int cantidadTorres,
    int cantidadPisos,
    int cantidadApartamentos,
    int cantidadEstacionamientos,
    int cantidadCarritos,
    int cantidadUsuarios,
    ConfiguracionResponse configuracion
) {
    public static DetalleCondominioResponse desdeAplicacion(com.condominios.sgc.application.dto.response.DetalleCondominioResponse app) {
        return new DetalleCondominioResponse(
                CondominioResponse.desdeAplicacion(app.condominio()),
                app.cantidadTorres(), app.cantidadPisos(), app.cantidadApartamentos(),
                app.cantidadEstacionamientos(), app.cantidadCarritos(), app.cantidadUsuarios(),
                app.configuracion() != null ? ConfiguracionResponse.desdeAplicacion(app.configuracion()) : null);
    }
}
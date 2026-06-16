package com.condominios.sgc.web.dto.response;

import com.condominios.sgc.domain.auxiliar.TipoVehiculo;

public record VehiculoResponse(
    Long id,
    String marca,
    String color,
    String modelo,
    String placa,
    TipoVehiculo tipo,
    Long idPropietario,
    Long idInquilino,
    Long idEstacionamiento,
    Long idCondominio
) {
    public static VehiculoResponse desdeAplicacion(com.condominios.sgc.application.dto.response.VehiculoResponse app) {
        return new VehiculoResponse(app.id(), app.marca(), app.color(), app.modelo(),
                app.placa(), app.tipo(), app.idPropietario(), app.idInquilino(),
                app.idEstacionamiento(), app.idCondominio());
    }
}

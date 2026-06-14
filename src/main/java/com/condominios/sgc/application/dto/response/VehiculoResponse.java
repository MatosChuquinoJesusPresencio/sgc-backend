package com.condominios.sgc.application.dto.response;

import com.condominios.sgc.domain.auxiliar.TipoVehiculo;
import com.condominios.sgc.domain.model.VehiculoModel;

public record VehiculoResponse(
    Long id,
    String marca,
    String color,
    String modelo,
    String placa,
    TipoVehiculo tipo,
    Long idPropietario,
    Long idInquilino,
    Long idEstacionamiento
) {
    public static VehiculoResponse desdeModelo(VehiculoModel model) {
        return new VehiculoResponse(
            model.getId(), model.getMarca(), model.getColor(),
            model.getModelo(), model.getPlaca(), model.getTipo(),
            model.getIdPropietario(), model.getIdInquilino(), model.getIdEstacionamiento());
    }
}

package com.condominios.sgc.infrastructure.web.dto;

import com.condominios.sgc.domain.auxiliar.TipoVehiculo;
import com.condominios.sgc.domain.model.VehiculoModel;

public record VehiculoResponse(
    Long id,
    String marca,
    String color,
    String modelo,
    String placa,
    TipoVehiculo tipo,
    Long propietarioId,
    Long inquilinoId,
    Long estacionamientoId
) {
    public static VehiculoResponse fromModel(VehiculoModel model) {
        return new VehiculoResponse(
            model.getId(),
            model.getMarca(),
            model.getColor(),
            model.getModelo(),
            model.getPlaca(),
            model.getTipo(),
            model.getPropietarioId(),
            model.getInquilinoId(),
            model.getEstacionamientoId()
        );
    }
}

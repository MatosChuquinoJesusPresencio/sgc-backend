package com.condominios.sgc.application.dto.result;

import java.util.List;

import com.condominios.sgc.domain.type.TipoVehiculo;

public record SecurityParkingSlotResult(
    Long id,
    Integer numero,
    TipoVehiculo tipoVehiculo,
    Integer capacidadMaxima,
    Integer cantidadActual,
    Boolean disponible,
    List<VehicleInfo> vehiculos
) {
    public record VehicleInfo(
        String placa, String tipo, String marca, String modelo, String color
    ) {}
}

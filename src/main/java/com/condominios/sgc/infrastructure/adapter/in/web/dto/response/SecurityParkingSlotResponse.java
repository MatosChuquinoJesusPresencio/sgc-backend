package com.condominios.sgc.infrastructure.adapter.in.web.dto.response;

import java.util.List;

public record SecurityParkingSlotResponse(
    Long id,
    Integer numero,
    String tipoVehiculo,
    Integer capacidadMaxima,
    Integer cantidadActual,
    Boolean disponible,
    List<VehicleInfo> vehiculos
) {
    public record VehicleInfo(
        String placa, String tipo, String marca, String modelo, String color
    ) {}
}

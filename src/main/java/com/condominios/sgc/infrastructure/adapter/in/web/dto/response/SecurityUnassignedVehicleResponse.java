package com.condominios.sgc.infrastructure.adapter.in.web.dto.response;

public record SecurityUnassignedVehicleResponse(
    Long idVehiculo,
    String placa,
    String marca,
    String modelo,
    String color,
    String tipo,
    String nombrePropietario
) {
}

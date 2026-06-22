package com.condominios.sgc.infrastructure.adapter.in.web.dto.response;

public record SecurityVehicleVerificationResponse(
    Long idVehiculo,
    String placa,
    String marca,
    String color,
    String modelo,
    String tipo,
    Long idPropietario,
    String nombrePropietario,
    Long idEstacionamiento
) {
}

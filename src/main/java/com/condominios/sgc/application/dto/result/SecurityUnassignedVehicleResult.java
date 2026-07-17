package com.condominios.sgc.application.dto.result;

public record SecurityUnassignedVehicleResult(
    Long idVehiculo,
    String placa,
    String marca,
    String modelo,
    String color,
    String tipo,
    String nombrePropietario
) {
}

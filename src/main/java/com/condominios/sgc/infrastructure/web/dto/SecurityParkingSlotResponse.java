package com.condominios.sgc.infrastructure.web.dto;

public record SecurityParkingSlotResponse(
    Long id,
    Integer numero,
    String tipoVehiculo,
    Integer capacidadMaxima,
    Integer cantidadActual,
    Boolean disponible
) {
}

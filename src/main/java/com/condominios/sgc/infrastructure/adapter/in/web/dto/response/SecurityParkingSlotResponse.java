package com.condominios.sgc.infrastructure.adapter.in.web.dto.response;

public record SecurityParkingSlotResponse(
    Long id,
    Integer numero,
    String tipoVehiculo,
    Integer capacidadMaxima,
    Integer cantidadActual,
    Boolean disponible
) {
}

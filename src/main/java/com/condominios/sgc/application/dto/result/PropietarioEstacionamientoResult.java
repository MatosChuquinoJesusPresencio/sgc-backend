package com.condominios.sgc.application.dto.result;

public record PropietarioEstacionamientoResult(
    Long id,
    Integer numero,
    String tipoVehiculo,
    Integer capacidadMaxima,
    Integer cantidadActual,
    Boolean disponible
) {
}

package com.condominios.sgc.application.dto.result;

import com.condominios.sgc.domain.type.TipoVehiculo;

public record SecurityParkingSlotResult(
    Long id,
    Integer numero,
    TipoVehiculo tipoVehiculo,
    Integer capacidadMaxima,
    Integer cantidadActual,
    Boolean disponible
) {
}

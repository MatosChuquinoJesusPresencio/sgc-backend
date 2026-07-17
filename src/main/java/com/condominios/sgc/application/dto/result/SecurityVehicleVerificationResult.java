package com.condominios.sgc.application.dto.result;

import com.condominios.sgc.domain.type.TipoVehiculo;

public record SecurityVehicleVerificationResult(
    Long idVehiculo,
    String placa,
    String marca,
    String color,
    String modelo,
    TipoVehiculo tipo,
    Long idPropietario,
    String nombrePropietario,
    Long idEstacionamiento,
    Long idEstacionamientoApartamento,
    String torreNombre,
    Integer numeroDepartamento,
    Boolean derechoEstacionamiento
) {
}

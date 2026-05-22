package com.condominios.sgc.application.dto;

import com.condominios.sgc.domain.auxiliar.TipoVehiculo;

public record VehiculoResponse(
        Long id,
        String marca,
        String color,
        String modelo,
        String placa,
        TipoVehiculo tipo,
        String propietarioId,
        Long inquilinoId) {
}

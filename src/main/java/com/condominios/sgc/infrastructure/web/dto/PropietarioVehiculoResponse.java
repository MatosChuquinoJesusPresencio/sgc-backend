package com.condominios.sgc.infrastructure.web.dto;

public record PropietarioVehiculoResponse(
    Long id,
    String marca,
    String color,
    String modelo,
    String placa,
    String tipo,
    Long idEstacionamiento
) {
}

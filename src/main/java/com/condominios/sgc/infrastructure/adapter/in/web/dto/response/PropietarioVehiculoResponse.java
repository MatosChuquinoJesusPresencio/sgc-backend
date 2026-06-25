package com.condominios.sgc.infrastructure.adapter.in.web.dto.response;

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

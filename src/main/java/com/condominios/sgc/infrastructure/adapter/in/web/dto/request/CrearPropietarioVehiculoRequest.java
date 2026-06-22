package com.condominios.sgc.infrastructure.adapter.in.web.dto.request;

public record CrearPropietarioVehiculoRequest(
    String marca,
    String color,
    String modelo,
    String placa,
    String tipo
) {
}

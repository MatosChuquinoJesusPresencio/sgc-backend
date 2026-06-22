package com.condominios.sgc.infrastructure.web.dto;

public record CrearPropietarioVehiculoRequest(
    String marca,
    String color,
    String modelo,
    String placa,
    String tipo
) {
}

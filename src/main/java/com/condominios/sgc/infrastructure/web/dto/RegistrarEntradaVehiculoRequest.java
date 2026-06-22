package com.condominios.sgc.infrastructure.web.dto;

public record RegistrarEntradaVehiculoRequest(
    String placa,
    String metodo,
    String ocupante,
    String datosInquilino
) {
}

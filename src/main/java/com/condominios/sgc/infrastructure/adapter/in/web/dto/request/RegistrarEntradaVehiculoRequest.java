package com.condominios.sgc.infrastructure.adapter.in.web.dto.request;

public record RegistrarEntradaVehiculoRequest(
    String placa,
    String metodo,
    String ocupante,
    String datosInquilino
) {
}

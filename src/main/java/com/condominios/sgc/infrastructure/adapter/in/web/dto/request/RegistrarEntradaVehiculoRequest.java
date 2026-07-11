package com.condominios.sgc.infrastructure.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;

public record RegistrarEntradaVehiculoRequest(
    @NotBlank String placa,
    @NotBlank String metodo,
    String ocupante,
    String datosInquilino,
    Long idEstacionamiento
) {
}

package com.condominios.sgc.infrastructure.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;

public record RegistrarPrestamoCarritoRequest(
    String codigoCarrito,
    Integer numeroApartamento,
    @NotBlank String nombreSolicitante,
    @NotBlank String dniSolicitante
) {
}

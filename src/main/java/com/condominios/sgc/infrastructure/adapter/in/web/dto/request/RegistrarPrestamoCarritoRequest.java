package com.condominios.sgc.infrastructure.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegistrarPrestamoCarritoRequest(
        @NotBlank String codigoCarrito,
    @NotNull Integer numeroApartamento,
    @NotBlank String nombreSolicitante,
    @NotBlank String dniSolicitante
) {
}

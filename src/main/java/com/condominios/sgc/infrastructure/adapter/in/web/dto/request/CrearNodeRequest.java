package com.condominios.sgc.infrastructure.adapter.in.web.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CrearNodeRequest(
    @NotBlank String tipo,
    @NotBlank String nombre,
    @NotBlank String nombreTorre,
    @NotNull Integer numero,
    @NotNull Integer numeroPiso,
    @NotNull Integer numeroApartamento,
    BigDecimal metraje
) {
}

package com.condominios.sgc.infrastructure.adapter.in.web.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;

public record CrearNodeRequest(
    @NotBlank String tipo,
    @NotBlank String nombre,
    @NotBlank String nombreTorre,
    Integer numero,
    Integer numeroPiso,
    Integer numeroApartamento,
    BigDecimal metraje
) {
}

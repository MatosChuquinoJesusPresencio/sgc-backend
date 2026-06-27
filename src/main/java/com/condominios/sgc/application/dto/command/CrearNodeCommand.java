package com.condominios.sgc.application.dto.command;

import java.math.BigDecimal;

public record CrearNodeCommand(
    String tipo,
    String nombre,
    String nombreTorre,
    Integer numero,
    Integer numeroPiso,
    Integer numeroApartamento,
    BigDecimal metraje
) {
}

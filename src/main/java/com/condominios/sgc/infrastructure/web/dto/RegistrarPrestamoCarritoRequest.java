package com.condominios.sgc.infrastructure.web.dto;

public record RegistrarPrestamoCarritoRequest(
    String codigoCarrito,
    Integer numeroApartamento,
    String nombreSolicitante,
    String dniSolicitante
) {
}

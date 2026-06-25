package com.condominios.sgc.infrastructure.adapter.in.web.dto.request;

public record RegistrarPrestamoCarritoRequest(
    String codigoCarrito,
    Integer numeroApartamento,
    String nombreSolicitante,
    String dniSolicitante
) {
}

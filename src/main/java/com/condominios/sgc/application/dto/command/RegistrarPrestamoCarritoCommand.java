package com.condominios.sgc.application.dto.command;

public record RegistrarPrestamoCarritoCommand(
    String codigoCarrito,
    Integer numeroApartamento,
    String nombreSolicitante,
    String dniSolicitante,
    String solicitante,
    Long idPropietario,
    Long idInquilino
) {
}

package com.condominios.sgc.application.dto.command;

public record CrearLogPrestamoCarritoCommand(
    String nombreSolicitante,
    String dniSolicitante,
    Long idApartamento,
    Long idCarrito,
    Long idPropietario,
    Long idInquilino
) {}

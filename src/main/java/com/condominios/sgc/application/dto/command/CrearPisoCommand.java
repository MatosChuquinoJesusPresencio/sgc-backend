package com.condominios.sgc.application.dto.command;

public record CrearPisoCommand(
    Integer numero,
    Long idTorre
) {}
package com.condominios.sgc.application.dto.command;

public record CrearTorreCommand(
    String nombre,
    Long idCondominio
) {}

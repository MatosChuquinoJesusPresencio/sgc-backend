package com.condominios.sgc.application.dto.command;

public record CrearCarritoCommand(
    String codigo,
    Long idCondominio
) {}

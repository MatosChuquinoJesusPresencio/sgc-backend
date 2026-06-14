package com.condominios.sgc.application.dto.command;

public record CrearEstacionamientoCommand(
    Integer numero,
    Long idCondominio
) {}

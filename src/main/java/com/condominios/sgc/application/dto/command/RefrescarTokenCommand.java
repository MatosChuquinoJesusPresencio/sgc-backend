package com.condominios.sgc.application.dto.command;

public record RefrescarTokenCommand(
    String token,
    Boolean recuerdame
) {}

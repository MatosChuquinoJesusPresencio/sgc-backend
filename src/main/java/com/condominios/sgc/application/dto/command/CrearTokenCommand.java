package com.condominios.sgc.application.dto.command;

import java.time.Instant;

import com.condominios.sgc.domain.auxiliar.TipoToken;

public record CrearTokenCommand(
    TipoToken tipo,
    String token,
    Instant expiracion,
    Long idUsuario
) {}

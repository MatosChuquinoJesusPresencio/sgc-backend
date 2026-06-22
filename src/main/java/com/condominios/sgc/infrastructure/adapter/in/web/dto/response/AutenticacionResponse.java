package com.condominios.sgc.infrastructure.adapter.in.web.dto.response;

public record AutenticacionResponse(
    UsuarioResponse usuario,
    Long expiracionAccesoMs,
    Long expiracionRefrescoMs
) {}

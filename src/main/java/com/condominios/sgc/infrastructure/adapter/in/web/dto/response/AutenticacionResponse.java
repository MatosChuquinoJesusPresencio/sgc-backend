package com.condominios.sgc.infrastructure.adapter.in.web.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta de autenticacion")
public record AutenticacionResponse(
    @Schema(description = "Informacion del usuario autenticado") UsuarioResponse usuario,
    @Schema(description = "Duracion del token de acceso en milisegundos") Long expiracionAccesoMs,
    @Schema(description = "Duracion del token de refresco en milisegundos") Long expiracionRefrescoMs
) {}

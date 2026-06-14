package com.condominios.sgc.application.dto.command;

public record SolicitarCambioCorreoCommand(
    Long idUsuario,
    String nuevoCorreo
) {}
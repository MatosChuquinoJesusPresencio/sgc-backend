package com.condominios.sgc.application.dto.command;

public record ActualizarPerfilCommand(
    String nombres,
    String apellidos,
    String telefono
) {}

package com.condominios.sgc.application.dto.command;

public record ActualizarMiUsuarioCommand(
    String nombres,
    String apellidos,
    String telefono
) {}

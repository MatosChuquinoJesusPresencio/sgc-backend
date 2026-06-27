package com.condominios.sgc.application.dto.command;

public record ActualizarAdministradorCommand(
    String nombres,
    String apellidos,
    String telefono
) {}

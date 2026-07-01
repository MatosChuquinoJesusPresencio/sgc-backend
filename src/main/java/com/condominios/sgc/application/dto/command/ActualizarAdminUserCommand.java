package com.condominios.sgc.application.dto.command;

public record ActualizarAdminUserCommand(
    String nombres,
    String apellidos,
    String telefono,
    String rol
) {
}

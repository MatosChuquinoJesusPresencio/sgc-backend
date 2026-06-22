package com.condominios.sgc.application.dto.command;

public record CrearAdminUserCommand(
    String nombres,
    String apellidos,
    String correo,
    String telefono,
    String contrasena,
    String rol
) {
}

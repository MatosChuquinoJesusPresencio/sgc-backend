package com.condominios.sgc.application.dto.command;

public record CrearAdministradorCommand(
    String nombres,
    String apellidos,
    String correo,
    String telefono,
    String contrasena
) {}

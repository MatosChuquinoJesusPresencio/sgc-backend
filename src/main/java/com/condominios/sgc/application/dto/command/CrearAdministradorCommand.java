package com.condominios.sgc.application.dto.command;

public record CrearAdministradorCommand(
    String nombres,
    String apellidos,
    String correo,
    String telefono,
    String contrasena
) {
    public CrearAdministradorCommand {
        if (nombres == null || nombres.isBlank()) throw new IllegalArgumentException("nombres no puede estar vacio");
        if (apellidos == null || apellidos.isBlank()) throw new IllegalArgumentException("apellidos no puede estar vacio");
        if (correo == null || correo.isBlank()) throw new IllegalArgumentException("correo no puede estar vacio");
        if (contrasena == null || contrasena.isBlank()) throw new IllegalArgumentException("contrasena no puede estar vacia");
    }
}

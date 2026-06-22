package com.condominios.sgc.application.dto.command;

public record ActualizarAdministradorCommand(
    String nombres,
    String apellidos,
    String telefono
) {
    public ActualizarAdministradorCommand {
        if (nombres == null || nombres.isBlank()) throw new IllegalArgumentException("nombres no puede estar vacio");
        if (apellidos == null || apellidos.isBlank()) throw new IllegalArgumentException("apellidos no puede estar vacio");
    }
}

package com.condominios.sgc.application.dto.command;

public record ActualizarMiCondominioCommand(
    String nombre,
    String direccion
) {
}

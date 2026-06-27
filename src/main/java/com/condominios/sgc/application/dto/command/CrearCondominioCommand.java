package com.condominios.sgc.application.dto.command;

public record CrearCondominioCommand(
    String nombre,
    Long idPais,
    Long idCiudad,
    String direccion
) {
}

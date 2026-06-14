package com.condominios.sgc.application.dto.command;

public record ActualizarCondominioCommand(
    String nombre,
    Long idPais,
    Long idCiudad,
    String direccion
) {}

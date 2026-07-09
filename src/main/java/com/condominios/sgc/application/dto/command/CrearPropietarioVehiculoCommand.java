package com.condominios.sgc.application.dto.command;

public record CrearPropietarioVehiculoCommand(
    String marca,
    String color,
    String modelo,
    String placa,
    String tipo,
    Long inquilinoId
) {
}

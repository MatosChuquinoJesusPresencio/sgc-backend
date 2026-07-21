package com.condominios.sgc.application.dto.command;

public record EditarPropietarioVehiculoCommand(
    String marca,
    String color,
    String modelo,
    String placa,
    Long inquilinoId
) {
}

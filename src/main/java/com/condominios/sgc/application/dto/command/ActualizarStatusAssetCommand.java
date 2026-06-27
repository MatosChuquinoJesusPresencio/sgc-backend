package com.condominios.sgc.application.dto.command;

public record ActualizarStatusAssetCommand(
    String tipo,
    String estado,
    Boolean disponible,
    String tipoVehiculo,
    Integer capacidadMaxima
) {
}

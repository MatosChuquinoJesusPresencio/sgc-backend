package com.condominios.sgc.application.dto.command;

public record CrearAssetCommand(
    String tipo,
    String codigo,
    Integer numero
) {
}

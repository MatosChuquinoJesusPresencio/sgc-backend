package com.condominios.sgc.application.dto.result;

public record AdminAssetResult(
    Long id,
    String tipo,
    String codigo,
    String estado,
    Integer numero,
    String tipoVehiculo,
    Integer capacidadMaxima,
    Integer cantidadActual,
    Boolean disponible,
    Long idApartamento,
    Long idCondominio
) {
}

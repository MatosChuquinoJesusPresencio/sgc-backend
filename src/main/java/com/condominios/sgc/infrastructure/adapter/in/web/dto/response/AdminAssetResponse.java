package com.condominios.sgc.infrastructure.adapter.in.web.dto.response;

public record AdminAssetResponse(
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

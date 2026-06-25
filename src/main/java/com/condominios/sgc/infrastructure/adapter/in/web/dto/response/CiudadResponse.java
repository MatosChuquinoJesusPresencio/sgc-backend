package com.condominios.sgc.infrastructure.adapter.in.web.dto.response;

public record CiudadResponse(
    Long id,
    String nombre,
    Long idPais
) {
}

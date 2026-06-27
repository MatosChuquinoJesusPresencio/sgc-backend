package com.condominios.sgc.infrastructure.adapter.in.web.dto.response;

public record AdminCondominioInfoResponse(
    Long id,
    String nombre,
    Long idPais,
    String nombrePais,
    Long idCiudad,
    String nombreCiudad,
    String direccion,
    Boolean activo,
    AdminConfiguracionResponse configuracion
) {
}

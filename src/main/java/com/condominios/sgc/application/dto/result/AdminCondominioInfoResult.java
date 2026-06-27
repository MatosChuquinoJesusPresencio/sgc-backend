package com.condominios.sgc.application.dto.result;

public record AdminCondominioInfoResult(
    Long id,
    String nombre,
    Long idPais,
    String nombrePais,
    Long idCiudad,
    String nombreCiudad,
    String direccion,
    Boolean activo,
    AdminConfiguracionResult configuracion
) {
}

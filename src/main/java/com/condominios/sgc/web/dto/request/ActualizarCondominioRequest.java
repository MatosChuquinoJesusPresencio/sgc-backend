package com.condominios.sgc.web.dto.request;

public record ActualizarCondominioRequest(
    String nombre,
    Long idPais,
    Long idCiudad,
    String direccion
) {}
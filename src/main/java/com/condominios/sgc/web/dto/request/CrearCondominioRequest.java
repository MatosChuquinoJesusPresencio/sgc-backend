package com.condominios.sgc.web.dto.request;

public record CrearCondominioRequest(
    String nombre,
    Long idPais,
    Long idCiudad,
    String direccion
) {}
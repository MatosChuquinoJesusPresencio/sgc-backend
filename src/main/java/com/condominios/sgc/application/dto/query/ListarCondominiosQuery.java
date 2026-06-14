package com.condominios.sgc.application.dto.query;

public record ListarCondominiosQuery(
    int pagina,
    int tamano,
    String nombre,
    Long idPais,
    Long idCiudad
) {}

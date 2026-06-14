package com.condominios.sgc.application.dto.query;

public record ListarPisosQuery(
    int pagina,
    int tamano,
    Integer numero,
    Long idTorre
) {}
package com.condominios.sgc.application.dto.query;

public record ListarTorresQuery(
    int pagina,
    int tamano,
    String nombre,
    Long idCondominio
) {}

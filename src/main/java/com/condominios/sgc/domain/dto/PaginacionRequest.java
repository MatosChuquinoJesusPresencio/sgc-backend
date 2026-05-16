package com.condominios.sgc.domain.dto;

import java.util.Map;

public record PaginacionRequest(
    int pagina,
    int tamanio,
    String ordenarPor,
    String direccion,
    Map<String, String> filtros
) {}

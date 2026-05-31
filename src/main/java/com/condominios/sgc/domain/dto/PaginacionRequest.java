package com.condominios.sgc.domain.dto;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public record PaginacionRequest(
    int pagina,
    int tamanio,
    String ordenarPor,
    String direccion,
    Map<String, String> filtros
) {

    public static PaginacionRequest desdeParams(Map<String, String> params) {
        int pagina = Integer.parseInt(params.getOrDefault("pagina", "0"));
        int tamanio = Integer.parseInt(params.getOrDefault("tamanio", "10"));
        String ordenarPor = params.get("ordenarPor");
        String direccion = params.getOrDefault("direccion", "ASC");

        Map<String, String> filtros = new HashMap<>(params);
        Set<String> keys = Set.of("pagina", "tamanio", "ordenarPor", "direccion");
        filtros.keySet().removeAll(keys);

        return new PaginacionRequest(pagina, tamanio, ordenarPor, direccion,
            filtros.isEmpty() ? null : filtros);
    }
}

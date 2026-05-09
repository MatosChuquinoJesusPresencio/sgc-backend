package com.condominios.sgc.domain.dto;

import java.util.Map;

public record PageRequestDto(
    int pagina,
    int tamano,
    String ordenar,
    Direccion direccion,
    Map<String, String> filtros
) {
    public enum Direccion { ASC, DESC }

    public PageRequestDto {
        if (pagina < 0) pagina = 0;
        if (tamano <= 0) tamano = 10;
        if (ordenar == null || ordenar.isBlank()) ordenar = "id";
    }

    public static PageRequestDto defaultRequest() {
        return new PageRequestDto(0, 10, "id", Direccion.ASC, Map.of());
    }
}

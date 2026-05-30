package com.condominios.sgc.domain.dto;

import java.util.List;

public record PaginacionResponse<T>(
    List<T> contenido,
    int pagina,
    int tamanio,
    long totalElementos,
    int totalPaginas
) {
    public static <T> PaginacionResponse<T> de(
        List<T> contenido,
        int pagina,
        int tamanio,
        long totalElementos,
        int totalPaginas
    ) {
        return new PaginacionResponse<>(
            contenido,
            pagina,
            tamanio,
            totalElementos,
            totalPaginas
        );
    }
}

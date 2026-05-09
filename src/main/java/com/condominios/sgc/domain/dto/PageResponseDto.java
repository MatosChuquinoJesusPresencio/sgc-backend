package com.condominios.sgc.domain.dto;

import java.util.List;

public record PageResponseDto<T>(
    List<T> contenido,
    int paginaActual,
    int tamanoPagina,
    long totalElementos,
    int totalPaginas,
    boolean primero,
    boolean ultimo
) {
    public static <T> PageResponseDto<T> of(
            List<T> contenido,
            int paginaActual,
            int tamanoPagina,
            long totalElementos,
            int totalPaginas,
            boolean primero,
            boolean ultimo) {
        return new PageResponseDto<>(contenido, paginaActual, tamanoPagina,
            totalElementos, totalPaginas, primero, ultimo);
    }
}

package com.condominios.sgc.domain.dto;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public record PaginacionResponse<T>(
    List<T> contenido,
    int pagina,
    int tamanio,
    long totalElementos,
    int totalPaginas
) {
    public <R> PaginacionResponse<R> map(Function<? super T, ? extends R> mapper) {
        return new PaginacionResponse<>(
            contenido.stream().map(mapper).collect(Collectors.toList()),
            pagina, tamanio, totalElementos, totalPaginas
        );
    }

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

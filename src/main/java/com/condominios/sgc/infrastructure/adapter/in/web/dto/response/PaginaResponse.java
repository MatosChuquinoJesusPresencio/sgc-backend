package com.condominios.sgc.infrastructure.adapter.in.web.dto.response;

import java.util.List;

import com.condominios.sgc.application.dto.result.PaginaResult;

public record PaginaResponse<T>(
    List<T> items,
    long total,
    int pagina,
    int tamano,
    int totalPaginas,
    boolean hayMas
) {
    public static <T> PaginaResponse<T> from(PaginaResult<T> pagina) {
        return new PaginaResponse<>(
            pagina.items(), pagina.total(), pagina.pagina(),
            pagina.tamano(), pagina.totalPaginas(), pagina.hayMas()
        );
    }
}

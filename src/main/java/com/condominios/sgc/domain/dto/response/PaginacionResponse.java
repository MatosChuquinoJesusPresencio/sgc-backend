package com.condominios.sgc.domain.dto.response;

import java.util.List;

public record PaginacionResponse<T>(
    List<T> contenido,
    int pagina,
    int tamano,
    long totalElementos,
    int totalPaginas
) {}

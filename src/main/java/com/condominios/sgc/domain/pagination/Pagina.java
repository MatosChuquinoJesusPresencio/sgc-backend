package com.condominios.sgc.domain.pagination;

import java.util.List;

public record Pagina<T>(
    List<T> contenido,
    int pagina,
    int tamano,
    long totalElementos,
    int totalPaginas
) {}

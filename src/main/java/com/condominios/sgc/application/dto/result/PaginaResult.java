package com.condominios.sgc.application.dto.result;

import java.util.List;

public record PaginaResult<T>(List<T> items, long total, int pagina, int tamano) {
    public int totalPaginas() {
        if (total == 0 || tamano == 0) return 0;
        return (int) Math.ceil((double) total / tamano);
    }

    public boolean hayMas() {
        return pagina + 1 < totalPaginas();
    }

    public static <T> PaginaResult<T> vacio() {
        return new PaginaResult<>(List.of(), 0, 0, 0);
    }
}

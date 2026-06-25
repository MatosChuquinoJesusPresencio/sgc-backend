package com.condominios.sgc.application.dto.query;

public record PaginaQuery(int pagina, int tamano) {
    public PaginaQuery {
        if (pagina < 0) pagina = 0;
        if (tamano < 1) tamano = 20;
    }

    public PaginaQuery() {
        this(0, 20);
    }
}

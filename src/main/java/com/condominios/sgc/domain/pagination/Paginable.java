package com.condominios.sgc.domain.pagination;

public record Paginable(
    int pagina, 
    int tamano
) {
    public Paginable {
        if (pagina < 0) pagina = 0;
        if (tamano < 1) tamano = 10;
    }
}

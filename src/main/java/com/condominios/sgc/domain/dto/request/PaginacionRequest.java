package com.condominios.sgc.domain.dto.request;

public record PaginacionRequest(
    int pagina, 
    int tamano
) {
    public PaginacionRequest {
        if (pagina < 0) pagina = 0;
        if (tamano < 1) tamano = 10;
    }
}

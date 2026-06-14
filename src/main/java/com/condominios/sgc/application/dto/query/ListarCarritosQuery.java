package com.condominios.sgc.application.dto.query;

import com.condominios.sgc.domain.auxiliar.EstadoCarrito;

public record ListarCarritosQuery(
    int pagina,
    int tamano,
    String codigo,
    EstadoCarrito estado,
    Long idCondominio
) {}

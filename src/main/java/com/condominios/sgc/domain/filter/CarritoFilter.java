package com.condominios.sgc.domain.filter;

import com.condominios.sgc.domain.auxiliar.EstadoCarrito;

public record CarritoFilter(
    String codigo, 
    EstadoCarrito estado, 
    Long idCondominio
) {}


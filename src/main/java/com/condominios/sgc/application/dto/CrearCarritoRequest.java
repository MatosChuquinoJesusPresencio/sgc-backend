package com.condominios.sgc.application.dto;

import com.condominios.sgc.domain.auxiliar.EstadoCarrito;

public record CrearCarritoRequest(
    String codigo,
    EstadoCarrito estadoInicial,
    Long condominioId
) {}

package com.condominios.sgc.web.dto;

public record CondominioRelationsResponse(
    long torres,
    long usuarios,
    long carritos,
    long configuraciones
) {}

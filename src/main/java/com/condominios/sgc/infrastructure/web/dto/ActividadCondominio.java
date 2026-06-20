package com.condominios.sgc.infrastructure.web.dto;

public record ActividadCondominio(
    Long id,
    String nombre,
    long carritoOps,
    long accesoOps,
    long totalOps
) {}

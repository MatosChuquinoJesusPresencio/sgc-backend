package com.condominios.sgc.web.dto;

public record ActividadCondominio(
    Long id,
    String nombre,
    long carritoOps,
    long accesoOps,
    long totalOps
) {}

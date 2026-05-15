package com.condominios.sgc.application.dto;

public record CrearCondominioRequest(
    String nombre,
    String pais,
    String ciudad,
    String direccion
) {}

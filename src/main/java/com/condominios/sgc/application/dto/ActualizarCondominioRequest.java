package com.condominios.sgc.application.dto;

public record ActualizarCondominioRequest(
    String nombre,
    String pais,
    String ciudad,
    String direccion
) {}

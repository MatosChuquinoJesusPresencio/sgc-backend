package com.condominios.sgc.application.dto;

public record CrearTorreRequest(
    String nombre,
    Long condominioId
) {}

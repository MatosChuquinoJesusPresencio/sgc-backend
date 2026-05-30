package com.condominios.sgc.application.dto;

public record CrearEstacionamientoRequest(
    Integer numero,
    Integer capacidadMaxima,
    Integer cantidadActual,
    Boolean disponible,
    Long condominioId
) {}

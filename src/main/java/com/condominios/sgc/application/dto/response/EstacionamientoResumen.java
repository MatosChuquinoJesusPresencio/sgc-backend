package com.condominios.sgc.application.dto.response;

public record EstacionamientoResumen(
    Long id,
    Integer numero,
    String tipoVehiculo,
    Boolean disponible
) {}

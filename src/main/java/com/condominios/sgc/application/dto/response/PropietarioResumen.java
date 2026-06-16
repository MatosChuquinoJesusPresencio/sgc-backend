package com.condominios.sgc.application.dto.response;

public record PropietarioResumen(
    Long id,
    String nombres,
    String apellidos,
    String correo,
    String telefono
) {}

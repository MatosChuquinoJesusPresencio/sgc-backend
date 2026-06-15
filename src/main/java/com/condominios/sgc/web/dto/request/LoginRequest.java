package com.condominios.sgc.web.dto.request;

public record LoginRequest(
    String correo,
    String contrasena,
    Boolean recuerdame
) {}

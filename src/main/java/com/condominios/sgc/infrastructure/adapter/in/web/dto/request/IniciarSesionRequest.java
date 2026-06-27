package com.condominios.sgc.infrastructure.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;

public record IniciarSesionRequest(
    @NotBlank String correo,
    @NotBlank String contrasena,
    Boolean recuerdame
) {}

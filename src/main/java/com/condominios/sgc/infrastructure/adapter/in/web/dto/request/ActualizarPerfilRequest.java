package com.condominios.sgc.infrastructure.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ActualizarPerfilRequest(
    @NotBlank String nombres,
    @NotBlank String apellidos,
    String telefono
) {}

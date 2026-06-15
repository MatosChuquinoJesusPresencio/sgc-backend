package com.condominios.sgc.web.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ActualizarMiUsuarioRequest(
    @NotBlank String nombres,
    @NotBlank String apellidos,
    @NotBlank String telefono
) {}

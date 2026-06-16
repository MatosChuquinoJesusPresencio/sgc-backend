package com.condominios.sgc.web.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ActualizarTorreRequest(
    @NotBlank String nombre
) {}

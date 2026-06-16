package com.condominios.sgc.web.dto.request;

import jakarta.validation.constraints.NotNull;

public record ActualizarPisoRequest(
    @NotNull Integer numero
) {}

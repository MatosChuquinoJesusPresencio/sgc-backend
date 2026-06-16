package com.condominios.sgc.web.dto.request;

import jakarta.validation.constraints.NotNull;

public record CrearPisoRequest(
    @NotNull Integer numero,
    @NotNull Long idTorre
) {}

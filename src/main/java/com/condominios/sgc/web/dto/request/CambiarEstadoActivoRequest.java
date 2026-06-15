package com.condominios.sgc.web.dto.request;

import jakarta.validation.constraints.NotNull;

public record CambiarEstadoActivoRequest(
    @NotNull Boolean activo
) {}

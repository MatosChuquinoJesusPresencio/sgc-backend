package com.condominios.sgc.infrastructure.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotNull;

public record EstadoActivoRequest(
    @NotNull Boolean activo
) {
}

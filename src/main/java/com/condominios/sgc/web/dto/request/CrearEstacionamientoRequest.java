package com.condominios.sgc.web.dto.request;

import jakarta.validation.constraints.NotNull;

public record CrearEstacionamientoRequest(
    @NotNull Integer numero,
    @NotNull Long idCondominio
) {}

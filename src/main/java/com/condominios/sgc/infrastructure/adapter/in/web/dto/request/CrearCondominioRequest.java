package com.condominios.sgc.infrastructure.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CrearCondominioRequest(
    @NotBlank String nombre,
    @NotNull Long idPais,
    @NotNull Long idCiudad,
    @NotBlank String direccion
) {
}

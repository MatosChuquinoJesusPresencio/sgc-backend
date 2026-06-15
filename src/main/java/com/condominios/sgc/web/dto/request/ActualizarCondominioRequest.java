package com.condominios.sgc.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ActualizarCondominioRequest(
    @NotBlank String nombre,
    @NotNull Long idPais,
    @NotNull Long idCiudad,
    String direccion
) {}
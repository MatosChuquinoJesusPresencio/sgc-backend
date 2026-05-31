package com.condominios.sgc.application.dto;

import com.condominios.sgc.domain.auxiliar.EstadoCarrito;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CrearCarritoRequest(
    @NotBlank String codigo,
    @NotNull EstadoCarrito estadoInicial,
    @NotNull Long condominioId
) {}
package com.condominios.sgc.application.dto;

import com.condominios.sgc.domain.auxiliar.Rol;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ActualizarUsuarioRequest(
    @NotBlank String nombres,
    @NotBlank String apellidos,
    String telefono,
    @NotNull Rol rol,
    Long condominioId
) {}
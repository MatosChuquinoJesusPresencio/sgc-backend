package com.condominios.sgc.web.dto.request;

import com.condominios.sgc.domain.auxiliar.Rol;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ActualizarUsuarioRequest(
    @NotBlank String nombres,
    @NotBlank String apellidos,
    @NotBlank String telefono,
    @NotNull Rol rol,
    @NotNull Long idCondominio,
    boolean desasignarCondominio
) {}

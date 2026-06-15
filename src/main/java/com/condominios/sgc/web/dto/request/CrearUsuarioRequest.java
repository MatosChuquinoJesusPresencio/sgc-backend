package com.condominios.sgc.web.dto.request;

import com.condominios.sgc.domain.auxiliar.Rol;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CrearUsuarioRequest(
    @NotBlank String nombres,
    @NotBlank String apellidos,
    @NotBlank @Email String correo,
    String telefono,
    @NotNull Rol rol,
    @NotNull Long idCondominio
) {}

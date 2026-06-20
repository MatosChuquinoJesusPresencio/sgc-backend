package com.condominios.sgc.application.dto.result;

import com.condominios.sgc.domain.type.Rol;

public record UsuarioActualResult(
    Long id,
    String nombres,
    String apellidos,
    Rol rol,
    Long idCondominio
) {}

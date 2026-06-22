package com.condominios.sgc.infrastructure.adapter.in.web.dto.response;

import com.condominios.sgc.domain.type.Rol;

public record UsuarioResponse(
    Long id,
    String nombres,
    String apellidos,
    String correo,
    Rol rol,
    Long idCondominio
) {}

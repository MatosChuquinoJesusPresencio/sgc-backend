package com.condominios.sgc.web.dto.response;

import com.condominios.sgc.domain.auxiliar.Rol;

public record LoginResponse(
    Long idUsuario,
    Rol rol,
    String nombres
) {}

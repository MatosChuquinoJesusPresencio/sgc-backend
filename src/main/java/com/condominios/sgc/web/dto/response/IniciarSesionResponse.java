package com.condominios.sgc.web.dto.response;

import com.condominios.sgc.domain.auxiliar.Rol;

public record IniciarSesionResponse(
    Long idUsuario,
    Rol rol,
    String nombres
) {}

package com.condominios.sgc.application.dto.response;

import com.condominios.sgc.domain.auxiliar.Rol;

public record IniciarSesionResponse(
    String token,
    String refreshToken,
    Long idUsuario,
    Rol rol,
    String nombres,
    boolean recuerdame
) {}
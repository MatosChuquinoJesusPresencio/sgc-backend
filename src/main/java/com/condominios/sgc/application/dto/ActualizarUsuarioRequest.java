package com.condominios.sgc.application.dto;

import com.condominios.sgc.domain.auxiliar.Rol;

public record ActualizarUsuarioRequest(
    String nombres,
    String apellidos,
    String telefono,
    Rol rol,
    Long condominioId
) {}

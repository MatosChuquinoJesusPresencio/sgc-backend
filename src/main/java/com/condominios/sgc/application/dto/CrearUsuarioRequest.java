package com.condominios.sgc.application.dto;

import com.condominios.sgc.domain.auxiliar.Rol;

public record CrearUsuarioRequest(
    String email,
    String password,
    String nombres,
    String apellidos,
    String telefono,
    Rol rol,
    Long condominioId
) {}

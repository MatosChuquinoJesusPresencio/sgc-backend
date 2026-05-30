package com.condominios.sgc.application.dto;

import com.condominios.sgc.domain.auxiliar.Rol;

public record CrearUsuarioRequest(
    String nombres,
    String apellidos,
    String correo,
    String telefono,
    Rol rol,
    Long condominioId,
    String contrasena
) {}

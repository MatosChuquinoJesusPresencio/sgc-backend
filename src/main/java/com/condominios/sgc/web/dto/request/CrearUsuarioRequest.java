package com.condominios.sgc.web.dto.request;

import com.condominios.sgc.domain.auxiliar.Rol;

public record CrearUsuarioRequest(
    String nombres,
    String apellidos,
    String correo,
    String telefono,
    Rol rol,
    Long idCondominio
) {}

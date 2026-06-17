package com.condominios.sgc.domain.filter;

import com.condominios.sgc.domain.auxiliar.Rol;

public record UsuarioFilter(
    String nombres,
    String apellidos,
    String correo,
    Rol rol,
    Boolean activo,
    Long idCondominio
) {}


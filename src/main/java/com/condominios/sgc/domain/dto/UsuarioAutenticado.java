package com.condominios.sgc.domain.dto;

import com.condominios.sgc.domain.auxiliar.Rol;

public record UsuarioAutenticado(
    String id,
    String correo,
    Rol rol
) {}

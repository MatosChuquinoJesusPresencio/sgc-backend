package com.condominios.sgc.domain.auxiliar;

public record UsuarioAutenticado(
    String id,
    String correo,
    Rol rol
) {}

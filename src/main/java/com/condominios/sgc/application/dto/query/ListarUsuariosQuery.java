package com.condominios.sgc.application.dto.query;

import com.condominios.sgc.domain.auxiliar.Rol;

public record ListarUsuariosQuery(
    int pagina,
    int tamano,
    String nombres,
    String apellidos,
    String correo,
    Rol rol,
    Boolean activo,
    Long idCondominio
) {}

package com.condominios.sgc.application.dto.command;

import com.condominios.sgc.domain.auxiliar.Rol;

public record CrearUsuarioCommand(
    String nombres,
    String apellidos,
    String correo,
    String telefono,
    Rol rol,
    Long idCondominio,
    Rol rolSolicitante,
    Long idCondominioSolicitante
) {}

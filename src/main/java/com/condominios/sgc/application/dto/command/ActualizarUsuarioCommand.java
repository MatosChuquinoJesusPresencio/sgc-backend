package com.condominios.sgc.application.dto.command;

import com.condominios.sgc.domain.auxiliar.Rol;

public record ActualizarUsuarioCommand(
    String nombres,
    String apellidos,
    String telefono,
    Rol rol,
    Long idCondominio,
    boolean desasignarCondominio
) {}

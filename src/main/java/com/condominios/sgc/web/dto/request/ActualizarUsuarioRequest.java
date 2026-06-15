package com.condominios.sgc.web.dto.request;

import com.condominios.sgc.domain.auxiliar.Rol;

public record ActualizarUsuarioRequest(
    String nombres,
    String apellidos,
    String telefono,
    Rol rol,
    Long idCondominio,
    boolean desasignarCondominio
) {}

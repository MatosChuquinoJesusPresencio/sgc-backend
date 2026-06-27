package com.condominios.sgc.application.port.out.service;

import com.condominios.sgc.domain.type.Rol;

public interface SecurityServicePort {
    Long obtenerIdUsuario();
    Rol obtenerRolAutenticado();
}
